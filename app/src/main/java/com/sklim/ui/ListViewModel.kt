package com.sklim.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sklim.adapter.ImageAdapter
import com.sklim.adapter.OnItemClickListener
import com.sklim.domain.usecase.ImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase: ImagesUseCase
) : ViewModel(), DefaultLifecycleObserver {
    val adapter = ImageAdapter()

    init {
        viewModelScope.launch {
            useCase.fetchImageList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    fun setAdapter(listener: OnItemClickListener) {
        adapter.listener = listener
    }
}