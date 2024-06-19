package com.example.harvest_savior_mobile_dev.data.retrofit

import com.example.harvest_savior_mobile_dev.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        fun getApiService(token : String? = null) : ApiService {
            val baseUrl = BuildConfig.BASE_URI_NEW

            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val clientB = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
            if (token != null){
                val authInterceptor = Interceptor { chain ->
                    val req = chain.request()
                    val requestHeader = req.newBuilder()
                        .addHeader("Authorization","Bearer $token")
                        .build()
                    chain.proceed(requestHeader)
                }
                clientB.addInterceptor(authInterceptor)

            }

            val client = clientB.build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}