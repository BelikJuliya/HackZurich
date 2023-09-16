package com.example.hackzurich.base

import android.view.ViewGroup
import com.example.hackzurich.domain.BaseModel

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun isValidType(baseModel: BaseModel): Boolean
}