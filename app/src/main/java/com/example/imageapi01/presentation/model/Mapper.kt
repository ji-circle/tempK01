package com.example.imageapi01.presentation.model

import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.entity.KakaoImageEntity


//여긴 내가 필요한것만 넣으면 되는거 맞나?
//fun KakaoImageEntity.toModel() = KakaoImageModel(
//    meta,
//
//)




fun DocumentEntity.toModel() = DocumentModel(
    thumbnailUrl,
    siteName,
    dateTime
)

fun DocumentModel.toEntity() = DocumentEntity(
    thumbnailUrl = thumbnailUrl,
    siteName = siteName,
    dateTime = dateTime
)