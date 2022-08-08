package com.gvelesiani.passworx.autofill

import android.annotation.SuppressLint
import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.service.autofill.*
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.collection.ArrayMap
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class PassworxAutofillService : AutofillService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val getPasswordsUseCase: GetPasswordsUseCase by inject()
    private val encryptionHelper: PasswordEncryptionHelper by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    @SuppressLint("Recycle")
    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal, callback: FillCallback
    ) {
        coroutineScope.launch {
            val structure =
                request.fillContexts.last().structure
            val emailFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
            val passFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
            val appName = structure.activityComponent.packageName

            val data = getPasswordsUseCase(false)

            identifyEmailFields(structure.getWindowNodeAt(0).rootViewNode, emailFields)
            identifyPassFields(structure.getWindowNodeAt(0).rootViewNode, passFields)

            val dataSets: ArrayList<Dataset> = ArrayList()

            if (emailFields.isNotEmpty() && passFields.isNotEmpty()) {
                var i = 0
                for (password in data) {
//                    if (appName.contains(
//                            password.websiteOrAppName.lowercase().replace("www.", "")
//                                .replace(".com", "").replace("m.", "")
//                        )
//                    ) {
                    data[i].websiteOrAppName.split("\\s".toRegex()).forEach { partName ->
                        if ((password.emailOrUserName.contains("@")) or (partName.lowercase(
                                Locale.ROOT
                            ).contains(
                                appName.lowercase(
                                    Locale.ROOT
                                )
                            )) or (appName.lowercase(Locale.ROOT)
                                .contains(partName.lowercase(Locale.ROOT)))
                        ) {
                            val remoteView = RemoteViews(packageName, R.layout.autofill_item)
                            remoteView.setTextViewText(
                                R.id.emailOrUserName,
                                password.emailOrUserName
                            )
                            remoteView.setTextViewText(R.id.passwordLabel, password.label)
                            dataSets.add(
                                Dataset.Builder(remoteView).setValue(
                                    emailFields[0]?.autofillId!!,
                                    AutofillValue.forText(password.emailOrUserName)
                                ).setValue(
                                    passFields[0]?.autofillId!!,
                                    AutofillValue.forText(encryptionHelper.decrypt(password.password))
                                ).build()
                            )
                        }
                    }
                    i += 1
                }
                //}
            } else if (passFields.isEmpty()) {
                var i = 0
                for (password in data) {
//                    if (appName.contains(
//                            password.websiteOrAppName.lowercase().replace("www.", "")
//                                .replace(".com", "").replace("m.", "")
//                        )
//                    ) {
                    data[i].websiteOrAppName.split("\\s".toRegex()).forEach { partName ->
                        if ((password.emailOrUserName.contains("@")) or (partName.lowercase(
                                Locale.ROOT
                            ).contains(
                                appName.lowercase(
                                    Locale.ROOT
                                )
                            )) or (appName.lowercase(Locale.ROOT)
                                .contains(partName.lowercase(Locale.ROOT)))
                        ) {
                            val remoteView = RemoteViews(packageName, R.layout.autofill_item)
                            remoteView.setTextViewText(
                                R.id.emailOrUserName,
                                password.emailOrUserName
                            )
                            dataSets.add(
                                Dataset.Builder(remoteView).setValue(
                                    emailFields[0]?.autofillId!!,
                                    AutofillValue.forText(password.emailOrUserName)
                                ).build()
                            )
                        }
                    }
                    i += 1
                }
            }

            if (dataSets.size != 0) {
                val response = FillResponse.Builder()
                for (dataz in dataSets) {
                    response.addDataset(dataz)
                }
                val responseBuilder = response.build()
                callback.onSuccess(responseBuilder)
            }
        }
    }

    private fun identifyEmailFields(
        node: AssistStructure.ViewNode,
        emailFields: MutableList<AssistStructure.ViewNode?>
    ) {
        if (node.className != null && node.className!!.contains("EditText") || node.className!!.contains(
                "TextInput"
            )
            || node.className!!.contains("textfield") || node.autofillType == AUTOFILL_TYPE_TEXT || node.className!!.contains(
                "android.widget.AutoCompleteTextView"
            ) //|| node.contentDescription!!.contains("Username")
//            || node.autofillHints!!.contains(AUTOFILL_HINT_EMAIL_ADDRESS)
//            || node.autofillHints!!.contains(AUTOFILL_HINT_USERNAME)
        ) {
            val hint = node.hint?.lowercase()
            val viewId = node.idEntry
            if ((hint != null && (hint.contains("email") ||
                        hint.contains("username") ||
                        hint.contains("name") ||
                        hint.contains("mail")) || (viewId != null && (viewId.contains("email") ||
                        viewId.contains("username") ||
                        viewId.contains("name") ||
                        viewId.contains("mail"))))
            ) {
                emailFields.add(node)
                return
            }
        }
        for (i in 0 until node.childCount) {
            identifyEmailFields(node.getChildAt(i), emailFields)
        }
    }

    private fun identifyPassFields(
        node: AssistStructure.ViewNode,
        passFields: MutableList<AssistStructure.ViewNode?>
    ) {
        if (node.className != null && node.className!!.contains("EditText") || node.className!!.contains(
                "TextInput"
            )
            || node.className!!.contains("textfield") || node.autofillType == AUTOFILL_TYPE_TEXT || node.className!!.contains(
                "android.widget.AutoCompleteTextView"
            ) //|| node.contentDescription!!.contains("Password")
//            || node.autofillHints!!.contains(AUTOFILL_HINT_PASSWORD)
        ) {
//            val viewId = node.hint?.lowercase()
//            if (viewId != null && (viewId.contains("password") ||
//                        viewId.contains("pass") ||
//                        viewId.contains("pin") ||
//                        viewId.contains("code") ||
//                        viewId.contains("secure"))
//            ) {
            val hint = node.hint?.lowercase()
            val viewId = node.idEntry
            if ((hint != null && (hint.contains("password") ||
                        hint.contains("pass") ||
                        hint.contains("pin") ||
                        hint.contains("code") || hint.contains("secure")) || (viewId != null && (viewId.contains(
                    "password"
                ) ||
                        viewId.contains("pass") ||
                        viewId.contains("pin") ||
                        viewId.contains("code") ||
                        viewId.contains("secure")))
                        )
            ) {
                passFields.add(node)
                return
            }
        }
        for (i in 0 until node.childCount) {
            identifyPassFields(node.getChildAt(i), passFields)
        }
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
    }

}