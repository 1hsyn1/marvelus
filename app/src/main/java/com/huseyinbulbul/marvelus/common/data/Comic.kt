package com.huseyinbulbul.marvelus.common.data

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("id") var id: Int?,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("thumbnail") var thumbnail: MarvelImage?
)