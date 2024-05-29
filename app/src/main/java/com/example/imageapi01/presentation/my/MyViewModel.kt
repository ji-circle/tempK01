package com.example.imageapi01.presentation.my

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.repository.ImageRepository
import com.example.imageapi01.domain.repository.SearchRepository
import com.example.imageapi01.presentation.model.DocumentModel
import com.example.imageapi01.presentation.model.toEntity
import com.example.imageapi01.presentation.model.toModel
import com.example.imageapi01.presentation.search.SearchViewModel
import kotlinx.coroutines.launch
import java.io.IOException


class MyViewModel(private val imageRepository: ImageRepository) : ViewModel() {
    private val _selectedItem = MutableLiveData<DocumentModel>()
    val selectedItem: LiveData<DocumentModel> get() = _selectedItem

    private val _isLikeStatus = MutableLiveData<Boolean>()
    val isLikeStatus: LiveData<Boolean> get() = _isLikeStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    val favoriteList: LiveData<List<DocumentModel>> = try {
        imageRepository.getImageData().map { list ->
            list.map {
                it.toModel()
            }
        }
    } catch (e: IOException) {
        MutableLiveData(emptyList())
    }


    fun toggleLike() {
        selectedItem.value?.let { item ->
            viewModelScope.launch {
                try {
                    val imageEntity = imageRepository.getDataByUrl(item.thumbnailUrl)
                    if (imageEntity == null) {
                        imageRepository.insertImageData(item.toEntity())
                        _isLikeStatus.postValue(true)
                    } else {
                        imageRepository.deleteImageData(item.toEntity())
                        _isLikeStatus.postValue(false)
                    }
                } catch (e: Exception) {
                    _error.postValue(e.message)
                }
            }
        }
    }
    fun setSelectedItem(selectedItem: DocumentModel) {
        _selectedItem.value = selectedItem
        viewModelScope.launch {
            try {
                val imageEntity = imageRepository.getDataByUrl(selectedItem.thumbnailUrl)
                _isLikeStatus.postValue(imageEntity != null)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

}

class MyViewModelFactory(private val imageRepository: ImageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(
                imageRepository = imageRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}