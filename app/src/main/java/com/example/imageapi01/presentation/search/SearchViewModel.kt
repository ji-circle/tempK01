package com.example.imageapi01.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.domain.repository.ImageRepository
import com.example.imageapi01.domain.repository.SearchRepository
import com.example.imageapi01.presentation.model.DocumentModel
import com.example.imageapi01.presentation.model.toEntity
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository, private val imageRepository: ImageRepository) : ViewModel(){
    private val _getImageModelList: MutableLiveData<List<DocumentModel>> = MutableLiveData()
    val getImageModelList: LiveData<List<DocumentModel>> get() = _getImageModelList


    //like 값 추적할거
    private val _selectedItem = MutableLiveData<DocumentModel>()
    val selectedItem: LiveData<DocumentModel> get() = _selectedItem

    private val _getImageById: MutableLiveData<DocumentEntity> = MutableLiveData()
    val getImageById: LiveData<DocumentEntity> get() = _getImageById

    private val _isLikeStatus = MutableLiveData<Boolean>()
    val isLikeStatus: LiveData<Boolean> get() = _isLikeStatus

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error





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

    fun getImageList(searchWord: String) = viewModelScope.launch{
        try {
            _getImageModelList.value = searchRepository.getImageList(searchWord).items.map {
                DocumentModel(
                    thumbnailUrl = it.thumbnailUrl,
                    dateTime = it.dateTime,
                    siteName = it.siteName
                )
            }
        }catch (e:Exception){
            emptyList<DocumentEntity>()
        }



    }


//    fun getImageById()


//    fun clearSearchById() {
//        _getImageById.value = null
//    }

}


class SearchViewModelFactory(private val searchRepository: SearchRepository, private val imageRepository: ImageRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                searchRepository = searchRepository,
                imageRepository = imageRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
