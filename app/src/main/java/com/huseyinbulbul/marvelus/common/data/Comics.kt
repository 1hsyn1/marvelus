package com.huseyinbulbul.marvelus.common.data

import com.google.gson.annotations.SerializedName

data class Comics(
    @SerializedName("items") var items: List<Comic>
){
    data class Comic(
        @SerializedName("name") var name: String?
    )
}