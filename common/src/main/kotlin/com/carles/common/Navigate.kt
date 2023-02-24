package com.carles.common

interface Navigate {
    fun up()
    fun toMonsterDetailFromMonsters(id: Int)
    fun toSettings()
    fun toErrorFromMonsters(errorMessage: String?)
    fun toErrorFromMonsterDetail(errorMessage: String?)
}
