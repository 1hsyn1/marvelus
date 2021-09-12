package com.huseyinbulbul.marvelus.common

import android.app.Application
import com.huseyinbulbul.marvelus.common.network.ApiConnector

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ApiConnector.initApi(this)
    }
}