package com.gvelesiani.passworx.autofill

import android.app.assist.AssistStructure
import android.content.Intent
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
import android.view.View.AUTOFILL_TYPE_TEXT
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
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

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal, callback: FillCallback
    ) {
        coroutineScope.launch {
            val structure =
                request.fillContexts.last().structure
            val emailFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
            val passwordFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
            val appName = structure.activityComponent.packageName

            val passwords = getPasswordsUseCase(false)

            identifyEmailFields(structure.getWindowNodeAt(0).rootViewNode, emailFields)
            identifyPasswordFields(structure.getWindowNodeAt(0).rootViewNode, passwordFields)

            val autofillDatasets: ArrayList<Dataset> = ArrayList()

            if (emailFields.isNotEmpty() && passwordFields.isNotEmpty()) {
                var i = 0
                for (password in passwords) {
                    val websiteOrAppName = password.websiteOrAppName
                        .lowercase()
                        .replace("www.", "")
                        .replace(".com", "")
                        .replace("m.", "")
                    if (appName.contains(websiteOrAppName)) {
                        passwords[i].websiteOrAppName.split("\\s".toRegex()).forEach { partName ->
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
                                autofillDatasets.add(
                                    Dataset.Builder(remoteView).setValue(
                                        emailFields.first()?.autofillId!!,
                                        AutofillValue.forText(password.emailOrUserName)
                                    ).setValue(
                                        passwordFields.first()?.autofillId!!,
                                        AutofillValue.forText(encryptionHelper.decrypt(password.password))
                                    ).build()
                                )
                            }
                        }
                        i += 1
                    }
                }
            } else if (passwordFields.isEmpty()) {
                var i = 0
                for (password in passwords) {
                    val websiteOrAppName = password.websiteOrAppName
                        .lowercase()
                        .replace("www.", "")
                        .replace(".com", "")
                        .replace("m.", "")
                    if (appName.contains(websiteOrAppName)) {
                        passwords[i].websiteOrAppName.split("\\s".toRegex()).forEach { partName ->
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
                                autofillDatasets.add(
                                    Dataset.Builder(remoteView).setValue(
                                        emailFields.first()?.autofillId!!,
                                        AutofillValue.forText(password.emailOrUserName)
                                    ).build()
                                )
                            }
                        }
                        i += 1
                    }
                }
            }

            if (autofillDatasets.size != 0) {
                val response = FillResponse.Builder()
                for (autofillDataset in autofillDatasets) {
                    response.addDataset(autofillDataset)
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
        if (checkClassNameAndAutofillType(node)) {
            if (checkHintsAndIdEntryForEmailFields(node)) {
                emailFields.add(node)
                return
            }
        }
        for (i in 0 until node.childCount) {
            identifyEmailFields(node.getChildAt(i), emailFields)
        }
    }

    private fun identifyPasswordFields(
        node: AssistStructure.ViewNode,
        passFields: MutableList<AssistStructure.ViewNode?>
    ) {
        if (checkClassNameAndAutofillType(node)) {
            if (checkHintsAndIdEntryForPasswordFields(node)) {
                passFields.add(node)
                return
            }
        }
        for (i in 0 until node.childCount) {
            identifyPasswordFields(node.getChildAt(i), passFields)
        }
    }

    private fun checkHintsAndIdEntryForEmailFields(node: AssistStructure.ViewNode): Boolean {
        val hint = node.hint?.lowercase()
        val viewId = node.idEntry
        return (hint != null && (hint.contains("email") ||
                hint.contains("username") ||
                hint.contains("name") ||
                hint.contains("mail")) ||
                (viewId != null && (viewId.contains("email") ||
                        viewId.contains("username") ||
                        viewId.contains("name") ||
                        viewId.contains("mail")))
                )
    }

    private fun checkHintsAndIdEntryForPasswordFields(node: AssistStructure.ViewNode): Boolean {
        val hint = node.hint?.lowercase()
        val viewId = node.idEntry
        return (hint != null && (hint.contains("password") ||
                hint.contains("pass") ||
                hint.contains("pin") ||
                hint.contains("code") ||
                hint.contains("secure")) ||
                (viewId != null && (viewId.contains("password") ||
                        viewId.contains("pass") ||
                        viewId.contains("pin") ||
                        viewId.contains("code") ||
                        viewId.contains("secure")))
                )
    }

    private fun checkClassNameAndAutofillType(node: AssistStructure.ViewNode): Boolean {
        return node.className != null && node.className!!.contains("EditText") || node.className!!.contains(
            "TextInput"
        ) || node.className!!.contains("textfield") || node.autofillType == AUTOFILL_TYPE_TEXT || node.className!!.contains(
            "android.widget.AutoCompleteTextView"
        )
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
    }

    companion object {

    }
}