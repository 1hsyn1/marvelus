package com.huseyinbulbul.marvelus.common.network

import android.content.Context
import com.huseyinbulbul.marvelus.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

class ApiConnector {
    companion object {
        private var marvelApi: MarvelApi? = null
        private var publicKey = ""
        private var privateKey = ""

        fun initApi(context: Context) {
            publicKey = context.getString(R.string.marvel_public_key)
            privateKey = context.getString(R.string.marvel_private_key)
            marvelApi = getRetrofit(context.getString(R.string.base_url)).create(MarvelApi::class.java)
        }

        fun getApi(): MarvelApi {
            if (marvelApi == null) {
                throw Throwable("initApi should be called first thing in App")
            }

            return marvelApi as MarvelApi
        }

        private fun getRetrofit(baseUrl: String): Retrofit {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder.connectTimeout(120, TimeUnit.SECONDS)
            builder.readTimeout(120, TimeUnit.SECONDS)
            builder.writeTimeout(120, TimeUnit.SECONDS)
            builder.addInterceptor { chain ->
                val timeStamp = "${System.currentTimeMillis()}"
                val hash = md5Encrypt("$timeStamp$privateKey$publicKey")
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("hash", hash)
                    .addQueryParameter("apikey", publicKey)
                    .build()

                chain.proceed(chain.request().newBuilder().url(url).build())
            }

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logger)

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun md5Encrypt(s: String): String? {
            try {
                val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
                messageDigest.update(s.toByteArray())
                val digested: ByteArray = messageDigest.digest()
                val hexString = StringBuilder()

                for (message in digested) {
                    var h = Integer.toHexString(0xFF and message.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }

                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return ""
        }


    }
}