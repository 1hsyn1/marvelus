package com.huseyinbulbul.marvelus.common.data

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") var int: Int?,
    @SerializedName("name")var name: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("thumbnail") var thumbnail: MarvelImage?,
    @SerializedName("comics") var comics: Comics?
)