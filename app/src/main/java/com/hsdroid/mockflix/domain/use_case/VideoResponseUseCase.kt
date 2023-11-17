package com.hsdroid.mockflix.domain.use_case

import com.hsdroid.mockflix.data.repository.VideoRepositoryImpl
import com.hsdroid.mockflix.domain.model.VideoResponse
import com.hsdroid.mockflix.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoResponseUseCase @Inject constructor(private val repositoryImpl: VideoRepositoryImpl) {
    operator fun invoke(): Flow<ResponseState<List<VideoResponse>>> = flow {
        try {
            emit(ResponseState.LOADING)
            val response = repositoryImpl.getVideoList().map {
                it.toVideoResponse()
            }
            emit(ResponseState.SUCCESS(response))
        } catch (e: Exception) {
            emit(ResponseState.FAILURE(e))
        }
    }
}