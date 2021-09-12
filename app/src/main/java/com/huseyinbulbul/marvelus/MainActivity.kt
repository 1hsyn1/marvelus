package com.huseyinbulbul.marvelus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huseyinbulbul.marvelus.common.network.ApiConnector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiConnector
            .getApi()
            .getComics(1009157)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("hus","success")
            }, {
                Log.i("hus","error")
            })
    }
}