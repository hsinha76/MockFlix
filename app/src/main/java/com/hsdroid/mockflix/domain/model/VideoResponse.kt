package com.hsdroid.mockflix.domain.model

data class VideoResponse(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val videoUrl: String
)