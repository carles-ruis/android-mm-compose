package com.carles.hyrule.domain

import com.carles.common.domain.AppSchedulers
import com.carles.hyrule.Monster
import com.carles.hyrule.data.HyruleRepo
import io.reactivex.Single

class RefreshMonstersUsecase(
    private val repository: HyruleRepo,
    private val schedulers: AppSchedulers
) {

    fun execute(): Single<List<Monster>> {
        return repository.refreshMonsters()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}