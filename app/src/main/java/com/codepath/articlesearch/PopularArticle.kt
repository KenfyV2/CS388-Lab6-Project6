package com.codepath.articlesearch

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PopularArticlesResponse(
    @SerialName("results")
    val results: List<PopularArticle>?
)

@Keep
@Serializable
data class PopularArticle(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String?,
    @SerialName("abstract")
    val abstract: String?,
    @SerialName("byline")
    val byline: String?,
    @SerialName("published_date")
    val publishedDate: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("media")
    val media: List<PopularMedia>?,
) : java.io.Serializable {
    // Create a function to get the image URL
    val imageUrl: String
        get() {
            // Try to get the first media item with images
            val mediaItem = media?.firstOrNull { it.type == "image" && it.mediaMetadata?.isNotEmpty() == true }
            // Get the medium size image or default to empty string
            return mediaItem?.mediaMetadata?.getOrNull(1)?.url ?: ""
        }
}

@Keep
@Serializable
data class PopularMedia(
    @SerialName("type")
    val type: String?,
    @SerialName("media-metadata")
    val mediaMetadata: List<MediaMetadata>?
) : java.io.Serializable

@Keep
@Serializable
data class MediaMetadata(
    @SerialName("url")
    val url: String?
) : java.io.Serializable