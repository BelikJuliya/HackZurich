package com.example.hackzurich.base

import androidx.recyclerview.widget.DiffUtil
import com.example.hackzurich.domain.model.BaseModel

class BaseDiffUtil<T : BaseModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.isIdDiff(newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem.isContentDiff(newItem)
    override fun getChangePayload(oldItem: T, newItem: T) = oldItem.getPayloadDiff(newItem)
}