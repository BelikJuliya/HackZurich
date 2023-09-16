package com.example.hackzurich.base

import android.view.ViewGroup
import com.example.hackzurich.domain.model.BaseModel

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun isValidType(baseModel: BaseModel): Boolean
}