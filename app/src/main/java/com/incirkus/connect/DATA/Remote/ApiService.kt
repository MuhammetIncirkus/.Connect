package com.incirkus.connect.DATA.Remote

import com.incirkus.connect.DATA.Model.APIResponse

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://get.api-feiertage.de"

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface ApiService {

    @GET("?")
    suspend fun getallHolidays(
        ): APIResponse

    @GET("?")
    suspend fun getStates(
        @Query("years") years: String = "2024,2025,2026",
        @Query("states") states: String = "nw",
    ): APIResponse

}

object CalendarApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}