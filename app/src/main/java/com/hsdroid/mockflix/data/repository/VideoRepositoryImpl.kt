package com.hsdroid.mockflix.data.repository

import com.hsdroid.mockflix.data.APIInterface
import com.hsdroid.mockflix.data.data_source.VideoResponseDTO
import com.hsdroid.mockflix.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val apiInterface: APIInterface) :
    VideoRepository {
    override suspend fun getVideoList(): List<VideoResponseDTO> {
        return apiInterface.getVideoList()
    }
}