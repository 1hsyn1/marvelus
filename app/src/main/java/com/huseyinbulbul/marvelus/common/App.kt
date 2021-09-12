package com.huseyinbulbul.marvelus.common

import android.app.Application
import com.huseyinbulbul.marvelus.common.network.ApiConnector
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ApiConnector.initApi(this)
    }
}