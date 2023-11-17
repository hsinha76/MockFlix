package com.hsdroid.mockflix.presentation.videolist

import com.hsdroid.mockflix.domain.model.VideoResponse

data class VideoListState(
    val isLoading: Boolean = false,
    val videoList: List<VideoResponse> = emptyList(),
    val error: Throwable? = null
)
