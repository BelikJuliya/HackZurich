package com.example.hackzurich.base

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.ViewBindingProperty
import by.kirich1409.viewbindingdelegate.viewBinding


inline fun <reified T : ViewBinding> Fragment.viewBinding(): ViewBindingProperty<Fragment, T> {
    return viewBinding()
}

enum class ActionType(val type: String) {
    SAVED("SAVED"), POPULAR("POPULAR")
}

fun getRealPathFromURIPath(contentURI: Uri, context: Context): String {
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

    context.contentResolver.query(contentURI, filePathColumn, null, null, null).use {
        return if (it == null) {
            contentURI.path.orEmpty()
        } else {
            try {
                it.moveToFirst()
                val idx = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                it.getString(idx)
            } catch (ex: Throwable) {
                ex.message?.let { it1 -> Log.e("TAG", it1) }
                ""
            }
        }
    }
}