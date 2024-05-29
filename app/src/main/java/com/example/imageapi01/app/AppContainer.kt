package com.example.imageapi01.app

import com.example.imageapi01.app.network.RetrofitClient
import com.example.imageapi01.data.remote.api.KakaoApiService
import com.example.imageapi01.data.repository.ImageRepositoryImpl
import com.example.imageapi01.data.repository.SearchRepositoryImpl
import com.example.imageapi01.domain.repository.ImageRepository
import com.example.imageapi01.domain.repository.SearchRepository
import com.example.imageapi01.presentation.my.MyViewModelFactory
import com.example.imageapi01.presentation.search.SearchViewModelFactory

class AppContainer {

    private val kakaoApiService = RetrofitClient.kakaoApiService

    val searchRepository: SearchRepository by lazy {
        SearchRepositoryImpl(kakaoApiService)
    }
    val imageRepository : ImageRepository by lazy {
        ImageRepositoryImpl(ImageApiApplication.getInstance()!!)
    }

    //var mainContainer: MainContainer?= null
    var searchContainer: SearchContainer?= null
    var myContainer: MyContainer?= null

}

class MyContainer(private val imageRepository: ImageRepository){
    val myViewModelFactory = MyViewModelFactory(imageRepository)
}

class SearchContainer(private val searchRepository: SearchRepository, private val imageRepository: ImageRepository) {
    val searchViewModelFactory = SearchViewModelFactory(searchRepository, imageRepository)

}

//class MainContainer()