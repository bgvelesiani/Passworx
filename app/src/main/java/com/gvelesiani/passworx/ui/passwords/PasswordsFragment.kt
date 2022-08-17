package com.gvelesiani.passworx.ui.passwords

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.adapters.PasswordAdapter
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.common.copyToClipboard
import com.gvelesiani.passworx.common.hideKeyboard
import com.gvelesiani.passworx.common.onTextChanged
import com.gvelesiani.passworx.constants.DATABASE_NAME
import com.gvelesiani.passworx.data.providers.local.database.PasswordDatabase
import com.gvelesiani.passworx.databinding.FragmentPasswordsBinding
import com.gvelesiani.passworx.domain.model.PasswordModel
import com.gvelesiani.passworx.ui.MainActivity
import com.gvelesiani.passworx.ui.MainVM
import com.gvelesiani.passworx.ui.passwordDetails.PasswordDetailsBottomSheet
import org.koin.android.ext.android.inject
import java.io.File


class PasswordsFragment :
    BaseFragment<PasswordsVM, FragmentPasswordsBinding>(PasswordsVM::class) {
    private lateinit var adapter: PasswordAdapter
    private val passwordDatabase: PasswordDatabase by inject()
    private val activityViewModel: MainVM by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPasswordsBinding
        get() = FragmentPasswordsBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        hideKeyboard()
        binding.toolbar.setupToolbar {
            findNavController().navigateUp()
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding.btAddPassword.visibility = View.VISIBLE
        viewModel.getPasswords()
        setupSearch()
        setupRecyclerViewAdapter()
        setOnClickListeners()

        binding.btBackup.setOnClickListener {
            viewModel.backup()
        }

        binding.btRestore.setOnClickListener {
            tryOpenFile()
        }
    }

    private fun setOnClickListeners() {
        binding.btAddPassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_passwords_to_addNewPasswordFragment)
        }
    }

    override fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            observeViewState(it)
        }
//        activityViewModel.backup.observe(viewLifecycleOwner) {
//            it?.let { roomBackup ->
//                roomBackup.database(passwordDatabase)
//                    .enableLogDebug(true)
//                    .backupIsEncrypted(true)
//                    .customEncryptPassword(BuildConfig.PASSWORX_BACKUP_ENCRYPT_KEY)
//                    .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_DIALOG)
//                    .maxFileCount(5)
//                    .apply {
//                        onCompleteListener { success, _, _ ->
//                            if (success) restartApp(
//                                Intent(
//                                    activity,
//                                    MainActivity::class.java
//                                )
//                            )
//                        }
//                    }
//                binding.btBackup.setOnClickListener {
//                    roomBackup.backup()
//                }
//
//                binding.btRestore.setOnClickListener {
//                    roomBackup.restore()
//                }
//            }
//        }
    }

    private fun observeViewState(viewState: PasswordsVM.ViewState) {
        binding.progressBar.isVisible = viewState.isLoading
        when (viewState.isLoading) {
            true -> {
                binding.rvPasswords.isVisible = false
                binding.groupNoData.isVisible = false
            }
            else -> {
                if (viewState.passwords.isEmpty()) {
                    binding.groupNoData.isVisible = true
                    binding.rvPasswords.isVisible = false
                } else {
                    adapter.submitData(viewState.passwords)
                    binding.groupNoData.isVisible = false
                    binding.rvPasswords.isVisible = true
                }
            }
        }
        viewState.decryptedPassword?.let {
            it.copyToClipboard(requireContext())
            val snackbar = Snackbar.make(
                requireView(),
                getString(R.string.password_copying_success),
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = binding.btAddPassword
            snackbar.show()
        }

        if (viewState.ff) {
            val file = File(passwordDatabase.openHelper.writableDatabase.path)
            createFile(file.path.toUri())
//
            Log.d("databasefileeee", file.name)
        }
    }

    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_TITLE, DATABASE_NAME)

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        startActivityForResult(intent, 25)
    }

    fun tryOpenFile() {
        val intentType = "*/*"
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = intentType
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(intentType))
        }

        startActivityForResult(intent, 26)
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?
    ) {
        if (requestCode == 26
            && resultCode == Activity.RESULT_OK
        ) {
            resultData?.data?.also { uri ->
                passwordDatabase.openHelper.close()
                context?.contentResolver?.openInputStream(uri)?.use { stream ->
                    val dbFile = context?.getDatabasePath(DATABASE_NAME)
                    dbFile?.delete()
                    stream.copyTo(dbFile!!.outputStream())
                }
                restartApp()
            }
        } else if (requestCode == 25 && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                passwordDatabase.openHelper.close()
                context?.contentResolver?.openOutputStream(uri)?.use { stream ->
                    context?.getDatabasePath(DATABASE_NAME)?.inputStream()?.copyTo(stream)
                }
            }
        }
    }

    private fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showMenu(v: View, @MenuRes menuRes: Int, password: PasswordModel) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menuEdit -> Toast.makeText(requireContext(), "Edit", Toast.LENGTH_SHORT).show()
                R.id.menuDelete -> {
                    MaterialAlertDialogBuilder(
                        requireContext()
                    )
                        .setMessage(getString(R.string.move_to_trash_dialog_message))
                        .setNegativeButton(getString(R.string.dialog_no)) { _, _ ->
                        }
                        .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                            viewModel.updateItemTrashState(
                                !password.isInTrash,
                                password.isFavorite,
                                password.passwordId
                            )
                            binding.search.text?.isNotEmpty()?.let { resetSearch(it) }
                            binding.rvPasswords.itemAnimator = DefaultItemAnimator()
                        }
                        .show()
                }
            }
            true
        }
        popup.setOnDismissListener {
        }
        popup.show()
    }

    private fun resetSearch(reset: Boolean) {
        if (reset) {
            binding.search.setText("")
            hideKeyboard()
            binding.search.clearFocus()
        }
    }

    private fun setupRecyclerViewAdapter() {
        adapter = PasswordAdapter(
            clickListener = { password: PasswordModel ->
                PasswordDetailsBottomSheet.show(
                    password,
                    childFragmentManager,
                    PasswordDetailsBottomSheet.TAG
                )
            },
            menuClickListener = { password: PasswordModel, view: View ->
                showMenu(
                    view,
                    R.menu.password_item_menu,
                    password
                )
            },
            copyClickListener = { passwordModel ->
                viewModel.decryptPassword(passwordModel.password)
            },
            favoriteClickListener = { passwordModel, position ->
                binding.search.text?.isNotEmpty()?.let { resetSearch(it) }
                viewModel.updateFavoriteState(!passwordModel.isFavorite, passwordModel.passwordId)
                adapter.notifyItemChanged(position)
            })
        binding.rvPasswords.adapter = adapter
        binding.rvPasswords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPasswords.itemAnimator = null
    }

    private fun setupSearch() {
        binding.btClearSearch.setOnClickListener {
            resetSearch(reset = true)
        }
        binding.search.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.btSearch.visibility = View.GONE
                binding.btClearSearch.animation =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
                binding.btClearSearch.visibility = View.VISIBLE
            } else {
                binding.btSearch.visibility = View.VISIBLE
                binding.btClearSearch.visibility = View.GONE
            }
        }
        binding.search.onTextChanged {
            viewModel.searchPasswords(it)
        }
    }
}