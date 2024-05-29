package com.example.imageapi01.domain.repository

import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.entity.KakaoImageEntity

interface SearchRepository {
    //검색 동영상 받아오기
    suspend fun getImageList(searchWord: String) : KakaoImageEntity
//    //url값으로 검색해서 가져오기
//    suspend fun getImageByUrl(url: String): KakaoImageEntity
}

//여기는 내가 원하는 거 만들면 되는 거 맞나?
//이게 homeViewModel에서 사용됨
//검색에서...