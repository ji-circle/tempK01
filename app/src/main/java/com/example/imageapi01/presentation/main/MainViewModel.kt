package com.example.imageapi01.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageapi01.presentation.model.KakaoImageModel

//이거 필요없지 않나...? bottom sheet 용이었으니까

class MainViewModel : ViewModel() {
    private val _selectedItem = MutableLiveData<KakaoImageModel?>()
    val selectedItem: LiveData<KakaoImageModel?> get() = _selectedItem

    fun setSelectedItem(selectedItem: KakaoImageModel?) {
        _selectedItem.value = selectedItem
    }
}