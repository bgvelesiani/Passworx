package com.gvelesiani.passworx.ui.backupAndRestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gvelesiani.passworx.base.BaseFragment
import com.gvelesiani.passworx.databinding.FragmentBackupAndRestoreBinding

class BackupAndRestoreFragment :
    BaseFragment<BackupAndRestoreVM, FragmentBackupAndRestoreBinding>(BackupAndRestoreVM::class) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBackupAndRestoreBinding =
        FragmentBackupAndRestoreBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun setupObservers() {

    }
}