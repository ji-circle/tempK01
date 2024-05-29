package com.example.imageapi01.data.remote.model

import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.entity.KakaoImageEntity

fun List<Documents>.asEntity(): List<DocumentEntity>{
    return map{
        DocumentEntity(
            it.thumbnailUrl,
            it.siteName,
            it.dateTime
        )
    }

}



fun KakaoImageResponse.toEntity() = KakaoImageEntity(
    meta = meta,
    items = documents.asEntity()
)