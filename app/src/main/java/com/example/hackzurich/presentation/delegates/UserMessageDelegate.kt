package com.example.hackzurich.presentation.delegates

import android.provider.ContactsContract.RawContacts.Data
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
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
        model as UserMessage
//        binding.messageDate.text = Data
        if (model.image == null) {
            bindMessage(model)
        } else {
            bindImage(model)
        }
    }

    private fun bindImage(model: BaseModel) {
        model as UserMessage
        with(binding) {
            ivPicture.isVisible = true
            tvMessage.isVisible = false
            Glide.with(itemView).load(model.bitmap).into(ivPicture)
        }
    }

    private fun bindMessage(model: BaseModel) {
        model as UserMessage
        with(binding) {
            ivPicture.isVisible = false
            tvMessage.isVisible = true
            tvMessage.text = model.message
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