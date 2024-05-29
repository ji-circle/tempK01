package com.example.imageapi01.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imageapi01.data.database.RoomEntity
import com.example.imageapi01.data.remote.model.Documents
import com.example.imageapi01.data.remote.model.KakaoImageResponse
import com.example.imageapi01.domain.entity.DocumentEntity
import org.w3c.dom.Document

interface ImageRepository {

    fun getImageData(): LiveData<List<DocumentEntity>>

    suspend fun insertImageData(image: DocumentEntity)

    suspend fun deleteImageData(image: DocumentEntity)

    suspend fun getDataByUrl(imageUrl: String): DocumentEntity?
}