package com.gvelesiani.passworx.autofill

import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.*
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
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val getPasswordsUseCase: GetPasswordsUseCase by inject()
    private val encryptionHelper: PasswordEncryptionHelper by inject()
    var node: AssistStructure.ViewNode? = null

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        coroutineScope.launch {
            // Get the structure from the request
            val context: List<FillContext> = request.fillContexts
            val structure: AssistStructure = context[context.size - 1].structure
            val emailFields = mutableListOf<AssistStructure.ViewNode>()
            val passwordFields = mutableListOf<AssistStructure.ViewNode>()
            identifyEmailFields(structure.getWindowNodeAt(0).rootViewNode, emailFields)
            identifyPasswordFields(structure.getWindowNodeAt(0).rootViewNode, passwordFields)

            // Do nothing if no email fields found
            if (emailFields.size == 0)
                return@launch
            val emailField = emailFields[0]
            val passwordField = passwordFields[0]

            // Fetch user data that matches the fields.
            val data = getPasswordsUseCase(false)

            node?.let {
                val password = data.find { model ->
                    it.idPackage?.lowercase()?.contains(model.websiteOrAppName.replace(".com", "").replace("www.", "").lowercase())
                        ?: false
                }
                val s = data[0]

                val usernamePresentation =
                    RemoteViews(packageName, R.layout.autofill_item)
                usernamePresentation.setTextViewText(R.id.passwordLabel, password?.emailOrUserName)
                usernamePresentation.setTextViewText(R.id.emailOrUserName, password?.websiteOrAppName)
//                val passwordPresentation =
//                    RemoteViews(packageName, android.R.layout.simple_list_item_1)
//                passwordPresentation.setTextViewText(android.R.id.text1, "Password for my_username")

                // Add a dataset to the response
                val fillResponse: FillResponse = FillResponse.Builder()
                    .addDataset(
                        Dataset.Builder()
                            .setValue(
                                emailField.autofillId!!,
                                AutofillValue.forText(password?.emailOrUserName),
                                usernamePresentation
                            )
                            .setValue(
                                passwordField.autofillId!!,
                                AutofillValue.forText(password?.password),
                                usernamePresentation
                            )
                            .build()
                    )
                    .build()
                // If there are no errors, call onSuccess() and pass the response
                callback.onSuccess(fillResponse)
            }
        }
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
    }

    private fun identifyEmailFields(
        node: AssistStructure.ViewNode,
        emailFields: MutableList<AssistStructure.ViewNode>
    ) {
        if (node.className?.contains("EditText") == true) {
            this.node = null
            this.node = node
            // check view id
            val viewId = node.idEntry
            if (viewId != null && (viewId.contains("email") || viewId.contains("username") || viewId.contains("Email"))) {
                emailFields.add(node)
                return
            }

            // check autofillHint
            val hints = (node.autofillHints ?: arrayOf()).toList()
            for (hint in hints) {
                if (viewId?.contains("email") == true || viewId?.contains("username") == true || viewId?.contains("Email") == true) {
                    emailFields.add(node)
                    return
                }
            }
        }
        for (i in 0 until node.childCount) {
            identifyEmailFields(node.getChildAt(i), emailFields)
        }
    }

    private fun identifyPasswordFields(
        node: AssistStructure.ViewNode,
        passwordFields: MutableList<AssistStructure.ViewNode>
    ) {
        if (node.className?.contains("EditText") == true) {
            this.node = null
            this.node = node
            // check view id
            val viewId = node.idEntry
            if (viewId != null && (viewId.contains("pass") || viewId.contains("password") || viewId.contains("Password"))) {
                passwordFields.add(node)
                return
            }

            // check autofillHint
            val hints = (node.autofillHints ?: arrayOf()).toList()
            for (hint in hints) {
                if (viewId?.contains("pass") == true || viewId?.contains("password") == true || viewId?.contains("Password") == true) {
                    passwordFields.add(node)
                    return
                }
            }
        }
        for (i in 0 until node.childCount) {
            identifyPasswordFields(node.getChildAt(i), passwordFields)
        }
    }
}