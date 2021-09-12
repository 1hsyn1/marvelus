package com.huseyinbulbul.marvelus.common.data

import com.google.gson.annotations.SerializedName
import com.huseyinbulbul.marvelus.common.network.ApiConnector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class Character(
    @SerializedName("id") var id: Int?,
    @SerializedName("name")var name: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("thumbnail") var thumbnail: MarvelImage?,
    @SerializedName("comics") var comics: Comics?
){
    var comicsDetails = mutableListOf<Comic>()

    //!!!!!!!!!!!!!!!! I am not using rxjava observers here to show variety
    fun getComics(success: (List<Comic>) -> Unit, fail: (String) -> Unit){
        id?.let {id ->
            ApiConnector
                .getApi()
                .getComics(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.data?.results?.let {list ->
                        comicsDetails = list
                        success(comicsDetails)
                    } ?: run {
                        fail("error")
                    }
                }, {
                    fail(it.localizedMessage)
                })
        }
    }
}