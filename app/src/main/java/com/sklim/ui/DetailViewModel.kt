package com.sklim.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sklim.domain.model.Images
import com.sklim.domain.usecase.ImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: ImagesUseCase
) : ViewModel(), DefaultLifecycleObserver {

    var image = MutableLiveData<Images.RS>()

    fun getImage(value: Int) {
        viewModelScope.launch {
            image.value = useCase.image(Images.RQ(value)).body()
        }
    }
}