package com.example.imageapi01.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imageapi01.databinding.SearchItemBinding
import com.example.imageapi01.presentation.model.DocumentModel
import java.text.SimpleDateFormat


class MyAdapter(
    private val onClick: (DocumentModel) -> Unit
): RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {


    var itemList = listOf<DocumentModel>()


    class ItemViewHolder(
        private val binding: SearchItemBinding,
        private val onClick: (DocumentModel) -> Unit
    ): RecyclerView.ViewHolder(binding.root){
        private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private var currentItem: DocumentModel? = null
        private var currentPosition = -1

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(documentModel: DocumentModel){
            currentItem = documentModel
            with(binding){
                Glide.with(binding.root)
                    .load(documentModel.thumbnailUrl)
                    .into(ivThumb)
                val date = dateFormat1.parse(documentModel.dateTime)
                tvDatetime.text = date?.let { dateFormat2.format(it) } ?: "오류"
                tvSource.text = documentModel.siteName
                itemView.setOnClickListener{
                    onClick(documentModel)
                }
                ivLike.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}