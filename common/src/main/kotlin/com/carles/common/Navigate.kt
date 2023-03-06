package com.carles.common

interface Navigate {
    fun toMonsterDetail(id: Int)
    fun toErrorDialog(errorMessage: String?)
}
