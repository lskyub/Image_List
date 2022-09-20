package com.sklim.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
) : ViewModel(), DefaultLifecycleObserver {

}