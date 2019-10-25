package com.jimmy.pagginglib_tutorial.networking

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

           return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
}