package com.carles.hyrule.domain

import com.carles.common.domain.AppSchedulers
import com.carles.hyrule.Monster
import com.carles.hyrule.data.HyruleRepo
import com.carles.hyrule.di.HyruleRepositoryQualifier
import io.reactivex.Single
import javax.inject.Inject

class RefreshMonsters @Inject constructor(
    @HyruleRepositoryQualifier
    private val repository: HyruleRepo,
    private val schedulers: AppSchedulers
) {

    fun execute(): Single<List<Monster>> {
        return repository.refreshMonsters()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}