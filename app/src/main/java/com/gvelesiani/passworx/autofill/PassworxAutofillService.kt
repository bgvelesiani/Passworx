package com.gvelesiani.passworx.autofill

import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.domain.useCases.GetPasswordsUseCase
import com.gvelesiani.passworx.helpers.encryptPassword.PasswordEncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@RequiresApi(Build.VERSION_CODES.O)
class PassworxAutofillService : AutofillService() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val getPasswordsUseCase: GetPasswordsUseCase by inject()
    private val encryptionHelper: PasswordEncryptionHelper by inject()
    private val emailFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
    private val passwordFields: MutableList<AssistStructure.ViewNode?> = ArrayList()
    private var currentEmail = ""
    private var currentPassword = ""

    override fun onFillRequest(p0: FillRequest, p1: CancellationSignal, p2: FillCallback) {
        coroutineScope.launch {
            val context: List<FillContext> = p0.fillContexts
            val structure: AssistStructure = context[context.size - 1].structure

            //getting values from the database
            val fillsList = getPasswordsUseCase(params = false)

            //Choosing a default password initially if there is some entry in the database
            if (fillsList.isNotEmpty()) {
                currentEmail = fillsList[0].emailOrUserName
                currentPassword = fillsList[0].password
            }

            //Looking for email and password fields
            identifyEmailFields(
                structure
                    .getWindowNodeAt(0)
                    .rootViewNode, emailFields, fillsList
            )

            identifyPasswordFields(
                structure
                    .getWindowNodeAt(0)
                    .rootViewNode, passwordFields, fillsList
            )

            if (emailFields.size == 0 && passwordFields.size == 0)
                return@launch

            //Views for displaying custom suggestion
//            val primaryEmailOrUsername = RemoteViews(packageName, R.layout.autofill_item)
//            val primaryPassword = RemoteViews(packageName, R.layout.autofill_item)
//
//            primaryEmailOrUsername.setTextViewText(R.id.emailOrUserName, currentEmail)
//            val emailField : AssistStructure.ViewNode? = if(emailFields.isEmpty()) null else emailFields[0]
//
//            primaryPassword.setTextViewText(R.id.passwordLabel, "Pass for $currentEmail")
//            val passwordField : AssistStructure.ViewNode? = if(passwordFields.isEmpty()) null else passwordFields[0]

            //Building dataset for email
            var primaryEmailDataSet: Dataset? = null

//            emailField?.let {
//                primaryEmailDataSet = Dataset.Builder(primaryEmailOrUsername)
//                    .setValue(
//                        it.autofillId!!,
//                        AutofillValue.forText(currentEmail)
//                    ).build()
//            }


            //Building dataset for password
            var primaryPasswordDataSet: Dataset? = null

//            passwordField?.let {
//                primaryPasswordDataSet = Dataset.Builder(primaryPassword)
//                    .setValue(
//                        it.autofillId!!,
//                        AutofillValue.forText(encryptionHelper.decrypt(currentPassword))
//                    ).build()
//            }

            val usernamePresentation = RemoteViews(packageName, R.layout.autofill_item)
            usernamePresentation.setTextViewText(R.id.emailOrUserName, currentEmail)
            usernamePresentation.setTextViewText(R.id.passwordLabel, "Password for $currentEmail")
//            val passwordPresentation = RemoteViews(packageName, R.layout.autofill_item)
//            passwordPresentation.setTextViewText(R.id.passwordLabel, "Password for ${currentEmail}")


            // This trash code has to be changed
            val emailField = if (emailFields.isNotEmpty()) emailFields[0]?.autofillId else null
            val passwordField = if (passwordFields.isNotEmpty()) passwordFields[0]?.autofillId else null
            var fillResponse: FillResponse? = null
            if (emailField != null && passwordField != null) {
                fillResponse = FillResponse.Builder()
                    .addDataset(
                        Dataset.Builder()
                            .setValue(
                                emailField,
                                AutofillValue.forText(currentEmail),
                                usernamePresentation
                            )
                            .setValue(
                                passwordField,
                                AutofillValue.forText(encryptionHelper.decrypt(currentPassword)),
                                usernamePresentation
                            )
                            .build()
                    )
                    .build()
            }


            //setting final response
//            val response = FillResponse.Builder().apply {
//                primaryEmailDataSet?.let {
//                    this.addDataset(it)
//                }
//                primaryPasswordDataSet?.let {
//                    this.addDataset(it)
//                }
//            }.build()

            // If there are no errors, call onSuccess() and pass the response
            p2.onSuccess(fillResponse)
        }
    }

    override fun onSaveRequest(p0: SaveRequest, p1: SaveCallback) {
        TODO("Not yet implemented")
    }

    private fun identifyEmailFields(
        node: AssistStructure.ViewNode?,
        emailFields: MutableList<AssistStructure.ViewNode?>?,
        fillsList: List<PasswordModel>?
    ) {
        //Checking if we have found the textfield or Edit Text field
        node!!.className?.let {
            if (it.contains("TextInput") || it.contains("textfield")
                || it.contains("user") || it.contains("EditText") || it.contains("emailAddress")
            ) {
                //Checking if the page contains the domain name we are looking for
                if (fillsList != null && node != null) {
                    fillsList.forEach {
                        val dName =
                            it.websiteOrAppName.trim().removePrefix("www.").removeSuffix(".com")
                        node!!.idPackage?.let { temp ->
                            if (temp.contains(dName)) {
                                currentEmail = it.emailOrUserName
                            }
                        }
                    }
                }

                val viewId = node.idEntry
                if (viewId != null && (viewId.contains("mail")
                            || viewId.contains("user") ||
                            viewId.contains("Name"))
                ) {
                    emailFields?.add(node)
                    return
                } else {
                    node!!.hint?.let { str ->
                        if (str.contains("mail")
                            || str.contains("user") ||
                            str.contains("Name")
                        ) {
                            emailFields?.add(node)
                            return
                        }
                    }
                }
            }
            //Checking all the nodes(items/views) that are displayed on the screen to look for email fields
            for (i in 0 until node.childCount) {
                identifyEmailFields(node.getChildAt(i), emailFields, fillsList)
            }
        }
    }

    private fun identifyPasswordFields(
        node: AssistStructure.ViewNode?,
        passwordFields: MutableList<AssistStructure.ViewNode?>?,
        fillsList: List<PasswordModel>?
    ) {
        node!!.className?.let {
            if (it.contains("TextInput") || it.contains("EditText")
                || it.contains("textfield")
            ) {
                //Checking if the page contains the domain name we are looking for
                if (fillsList != null && node != null) {
                    fillsList.forEach {
                        val dName =
                            it.websiteOrAppName.trim().removePrefix("www.").removeSuffix(".com")
                        node.idPackage?.let { temp ->
                            if (temp.contains(dName)) {
                                currentPassword = it.password
                            }
                        }
                    }
                }


                val viewId = node.idEntry
                if (viewId != null && (viewId.contains("password"))
                ) {
                    passwordFields?.add(node)
                    return
                } else {
                    node!!.hint?.let { str ->
                        if (str.contains("password")
                        ) {
                            passwordFields?.add(node)
                            return
                        }
                    }
                }
            }
            //Checking all the nodes(items/views) that are displayed on the screen to look for password fields
            for (i in 0 until node.childCount) {
                identifyPasswordFields(node.getChildAt(i), passwordFields, fillsList)
            }
        }
    }
}