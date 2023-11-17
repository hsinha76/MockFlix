package com.hsdroid.mockflix.presentation.videolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsdroid.mockflix.domain.use_case.VideoResponseUseCase
import com.hsdroid.mockflix.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videoResponseUseCase: VideoResponseUseCase) :
    ViewModel() {

    private val _response = MutableStateFlow(VideoListState())

    val response = _response.asStateFlow()

    init {
        getVideoList()
    }

    private fun getVideoList() = viewModelScope.launch {
        videoResponseUseCase.invoke().collect {
            when (it) {
                is ResponseState.LOADING -> {
                    _response.value = VideoListState(isLoading = true)
                }

                is ResponseState.SUCCESS -> {
                    _response.value = VideoListState(videoList = it.data)
                }

                is ResponseState.FAILURE -> {
                    _response.value = VideoListState(error = it.t)
                }
            }
        }
    }
}