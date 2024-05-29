package com.example.imageapi01.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageapi01.databinding.SearchItemBinding
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.presentation.model.DocumentModel
import java.text.SimpleDateFormat

class SearchAdapter(
    private val onClick: (DocumentModel, Int) -> Unit,
) : RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>(){
    var itemList = listOf<DocumentModel>()

    class SearchItemViewHolder(
        private val binding: SearchItemBinding,
        val onClick: (DocumentModel, Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private var currentItem: DocumentModel? = null
        private var currentPosition = -1


        //ViewHolder가 초기화될 때 호출되어, itemView의 클릭 리스너를 설정... 클릭 시 onClick 함수를 호출
        //이거 그냥 다시 바꾸기!!
        init {

            itemView.setOnClickListener{
                currentItem?.let{
                    onClick(it, currentPosition)
                }
            }
        }

        fun bind(documentModel: DocumentModel, position: Int){
            currentItem = documentModel
            currentPosition = position

            with(binding){
                Glide.with(binding.root).load(documentModel.thumbnailUrl).into(ivThumb)
                val date = dateFormat1.parse(documentModel.dateTime)
                tvDatetime.text = date?.let { dateFormat2.format(it) } ?: "오류"
                tvSource.text = documentModel.siteName
                ivLike.isVisible = false
//                ivLike.isVisible = searchViewModel.isLikeStatus.value?.get(documentEntity.thumbnailUrl) ?: false

                // ivLike 버튼 상태 업데이트

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }
}