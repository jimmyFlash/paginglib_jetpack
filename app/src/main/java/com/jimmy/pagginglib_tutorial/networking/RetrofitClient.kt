package com.jimmy.pagginglib_tutorial.networking

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


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
        logger.level = HttpLoggingInterceptor.Level.HEADERS
/*
            val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()*/

        // use this client when behind a firewall or proxy
        val client = getUnsafeOkHttpClient()
            .addInterceptor(logger)
            .build()

       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    /**
     * enables okhttp to accept untrusted certificates (when behind firewall or proxy)
     */
    private fun getUnsafeOkHttpClient() : OkHttpClient.Builder{

        lateinit var builder : OkHttpClient.Builder

        try {

            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    //
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    //
                }
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            })
            // Create a trust manager that does not validate certificate chains
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val  sslSocketFactory : SSLSocketFactory = sslContext.socketFactory

            builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)

            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            return builder
        } catch ( e : Exception) {
//                throw  RuntimeException(e)
            Log.e("OKHTTP error >>>>>>>>" ," ${e.message}")
            return OkHttpClient.Builder()
        }

    }
}

