package com.example.imageapi01.domain.entity

import com.example.imageapi01.data.remote.model.Meta

data class KakaoImageEntity(
    val meta: Meta,
    val items: List<DocumentEntity>
)
data class DocumentEntity(
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String,
    //var isLike: Boolean = false
)