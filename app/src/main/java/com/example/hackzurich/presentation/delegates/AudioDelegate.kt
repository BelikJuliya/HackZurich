package com.example.hackzurich.presentation.delegates

import android.view.ViewGroup
import com.example.hackzurich.R
import com.example.hackzurich.base.AdapterDelegate
import com.example.hackzurich.base.BaseViewHolder
import com.example.hackzurich.databinding.ItemAudioMessageBinding
import com.example.hackzurich.domain.model.AudioMessage
import com.example.hackzurich.domain.model.BaseModel
import com.example.hackzurich.domain.model.UserMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioMessageViewHolderHolder(
    val parent: ViewGroup,
) : BaseViewHolder(parent, R.layout.item_audio_message) {

    private lateinit var binding: ItemAudioMessageBinding
    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemAudioMessageBinding.bind(itemView)
        model as AudioMessage
        with(binding) {
            val dateString = dateFormat.format(Date())
            messageDate.text = "Sent $dateString"
        }
    }
}

class AudioDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        AudioMessageViewHolderHolder(
            parent,
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is AudioMessage
}