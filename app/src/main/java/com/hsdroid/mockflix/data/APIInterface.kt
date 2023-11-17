package com.hsdroid.mockflix.data

import com.hsdroid.mockflix.data.data_source.VideoResponseDTO
import retrofit2.http.GET

interface APIInterface {

    @GET("/hsinha76/MockFlix/main/videos.json")
    suspend fun getVideoList(): List<VideoResponseDTO>
}