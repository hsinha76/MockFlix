package com.hsdroid.mockflix.domain.repository

import com.hsdroid.mockflix.data.data_source.VideoResponseDTO
import com.hsdroid.mockflix.domain.model.VideoResponse

interface VideoRepository {
    suspend fun getVideoList(): List<VideoResponseDTO>
}