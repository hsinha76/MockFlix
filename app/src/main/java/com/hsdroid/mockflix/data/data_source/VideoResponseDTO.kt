package com.hsdroid.mockflix.data.data_source

import com.google.gson.annotations.SerializedName
import com.hsdroid.mockflix.domain.model.VideoResponse

data class VideoResponseDTO(
    @SerializedName("duration")
    val duration: String = "",
    @SerializedName("subscriber")
    val subscriber: String = "",
    @SerializedName("isLive")
    val isLive: Boolean = false,
    @SerializedName("videoUrl")
    val videoUrl: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("uploadTime")
    val uploadTime: String = "",
    @SerializedName("views")
    val views: String = "",
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String = ""
) {
    fun toVideoResponse(): VideoResponse {
        return VideoResponse(title, description, thumbnailUrl, videoUrl)
    }
}