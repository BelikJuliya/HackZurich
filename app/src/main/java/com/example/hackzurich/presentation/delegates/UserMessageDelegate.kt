package com.example.hackzurich.presentation.delegates

import android.view.ViewGroup
import com.example.hackzurich.R
import com.example.hackzurich.base.AdapterDelegate
import com.example.hackzurich.base.BaseViewHolder
import com.example.hackzurich.databinding.ItemUserMessageBinding
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.BaseModelPayload
import com.example.hackzurich.domain.model.UserMessage

class UserMessageHolder(
    val parent: ViewGroup,
) : BaseViewHolder(parent, R.layout.item_user_message) {

    private lateinit var binding: ItemUserMessageBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemUserMessageBinding.bind(itemView)
        with(binding) {
            model as UserMessage
            bindMessage(model)
        }
    }

    private fun bindMessage(model: BaseModel) {
        model as UserMessage
        with(binding) {
            botMessage.text = model.message
        }
    }


    override fun bindPayload(
        model: BaseModel,
        viewHolder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {
        payloads.forEach {
            when (it) {
                BaseModelPayload.MESSAGE -> bindMessage(model)
            }
        }
    }
}

class UserMessageDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        UserMessageHolder(
            parent,
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is UserMessage
}