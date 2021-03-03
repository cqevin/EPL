package com.chriskevin.epl.util

class AdapterListener<T>(val clickListener: (T) -> Unit) {
    fun onClick(data: T) = clickListener(data)
}