package com.huseyinbulbul.marvelus.common.data

import com.google.gson.annotations.SerializedName

data class MarvelImage(
    @SerializedName("path") var path: String?,
    @SerializedName("extension") var extension: String?
)