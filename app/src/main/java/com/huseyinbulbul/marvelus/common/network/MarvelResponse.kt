package com.huseyinbulbul.marvelus.common.network

import com.google.gson.annotations.SerializedName

data class MarvelResponse<T>(
    @SerializedName("code") var code: Int?,
    @SerializedName("status") var status: String?,
    @SerializedName("copyright") var copyright: String?,
    @SerializedName("attributionText") var attributionText: String?,
    @SerializedName("attributionHTML") var attributionHTML: String?,
    @SerializedName("etag") var etag: String?,
    @SerializedName("data") var data: T?
)