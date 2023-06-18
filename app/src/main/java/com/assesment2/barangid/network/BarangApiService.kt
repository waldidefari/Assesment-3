package com.assesment2.barangid.network

import com.assesment2.barangid.ui.barang.DataBarang
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET




interface BarangApi {
    @GET("barang.json")
    suspend fun getBarang(): List<DataBarang>
}


object BarangApiService {
    private const val BASE_URL = "https://raw.githubusercontent.com/" +
            "waldidefari/API_barang/main/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofitSuku = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    //Retrofit Create


    val service: BarangApi by lazy {
        retrofitSuku.create(BarangApi::class.java)
    }

    //Function


    fun getBarangUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg,jpeg"
    }
}


enum class ApiStatus { LOADING, SUCCES,FAILED}
