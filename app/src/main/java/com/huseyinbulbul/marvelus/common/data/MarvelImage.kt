package com.huseyinbulbul.marvelus.common.data

import android.util.Log
import com.google.gson.annotations.SerializedName

data class MarvelImage(
    @SerializedName("path") var path: String?,
    @SerializedName("extension") var extension: String?
){
    fun getSmallUrl(): String{
        return "$path/standard_small.$extension"
    }

    fun getBigUrl(): String{
        return "$path/standard_fantastic.$extension"
    }
}