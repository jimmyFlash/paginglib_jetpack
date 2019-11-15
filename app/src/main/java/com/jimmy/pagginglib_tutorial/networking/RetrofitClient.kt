package com.jimmy.pagginglib_tutorial.networking

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
        private var mInstance: Api? = null
        private const val BASE_URL = "https://api.stackexchange.com/2.2/"

        @Synchronized
        fun getInstance(): Api {
            if (mInstance == null) {
                mInstance = create()
            }
            return mInstance as Api
        }

        private fun create (): Api {
            Log.e(RetrofitClient::class.java.simpleName, "RetrofitClient created" )

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            
           return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
}