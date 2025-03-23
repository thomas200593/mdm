package com.thomas200593.mdm.core.ui.component.permission

import androidx.lifecycle.ViewModel
import com.thomas200593.mdm.core.ui.component.permission.repository.RepoPermissionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val repoPermissionHandler: RepoPermissionHandler
) : ViewModel() {
    
}