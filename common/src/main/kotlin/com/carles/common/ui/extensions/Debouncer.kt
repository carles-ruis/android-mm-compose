package com.carles.common.ui.extensions

object Debouncer {
    private var lastClickTime: Long = 0

    operator fun invoke(debounceTime: Long = 500L, onClick: () -> Unit) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime < debounceTime) return
        lastClickTime = now
        onClick()
    }
}