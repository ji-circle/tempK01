package com.example.imageapi01.presentation.model

import android.os.Parcelable
import com.example.imageapi01.data.remote.model.Meta
import com.example.imageapi01.domain.entity.DocumentEntity

data class KakaoImageModel(
    val meta: Meta,
    val items: List<DocumentModel>
)

//@Parcelize
data class DocumentModel(
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String
)
    //var isLike: Boolean = false
//):Parcelable