package com.huseyinbulbul.marvelus.common.network

import com.google.gson.annotations.SerializedName

data class MarvelListData<T>(
    @SerializedName("offset") var offset: Int?,
    @SerializedName("limit") var limit: Int?,
    @SerializedName("total") var total: Int?,
    @SerializedName("count") var count: Int?,
    @SerializedName("results") var results: MutableList<T>?
)