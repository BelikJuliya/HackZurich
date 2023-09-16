package com.example.hackzurich.base

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