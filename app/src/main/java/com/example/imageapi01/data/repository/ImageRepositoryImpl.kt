package com.example.imageapi01.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.imageapi01.app.network.RetrofitClient.kakaoApiService
import com.example.imageapi01.data.database.ImageRoomDatabase
import com.example.imageapi01.data.database.RoomDao
import com.example.imageapi01.data.database.RoomEntity
import com.example.imageapi01.data.remote.api.KakaoApiService
import com.example.imageapi01.data.remote.model.KakaoImageResponse
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.repository.ImageRepository

class ImageRepositoryImpl(val context: Context) : ImageRepository {

    private val roomDB: ImageRoomDatabase = ImageRoomDatabase.getInstance(context)!!
    private val roomDao: RoomDao = roomDB.getRoomDao()

    override fun getImageData(): LiveData<List<DocumentEntity>> {

        val entityList = roomDao.getAllData().map { room ->
            room.map {
                DocumentEntity(
                    it.thumbnailUrl,
                    it.siteName,
                    it.dateTime
                )
            }
        }
        return entityList

    }

    override suspend fun insertImageData(image: DocumentEntity) {
        val roomData = RoomEntity(
            image.thumbnailUrl,
            image.siteName,
            image.dateTime
        )
        roomDao.insertData(roomData)
    }


    override suspend fun deleteImageData(image: DocumentEntity) {
        val roomData = RoomEntity(
            image.thumbnailUrl,
            image.siteName,
            image.dateTime
        )
        roomDao.deleteData(roomData)
    }

    override suspend fun getDataByUrl(imageUrl: String): DocumentEntity? {
        val roomEntity = roomDao.getDataByUrl(imageUrl)
        return roomEntity?.let {
            DocumentEntity(
                it.thumbnailUrl,
                it.siteName,
                it.dateTime
            )
        }
    }

}

