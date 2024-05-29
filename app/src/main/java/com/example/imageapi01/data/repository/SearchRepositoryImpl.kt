package com.example.imageapi01.data.repository

import com.example.imageapi01.data.remote.api.KakaoApiService
import com.example.imageapi01.data.remote.model.toEntity
import com.example.imageapi01.domain.entity.KakaoImageEntity
import com.example.imageapi01.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val kakaoApiService: KakaoApiService
) : SearchRepository {
    override suspend fun getImageList(searchWord: String): KakaoImageEntity {
        return kakaoApiService.getImage(searchWord).toEntity()
    }

}