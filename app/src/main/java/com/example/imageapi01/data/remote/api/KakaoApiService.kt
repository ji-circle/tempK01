package com.example.imageapi01.data.remote.api

import com.example.imageapi01.data.remote.model.KakaoImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApiService {
    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") searchWord: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80,
        @Header("Authorization") key: String = "KakaoAK 17c301917c29921a42b38c39707743d4"
    ): KakaoImageResponse

}