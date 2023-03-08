package com.carles.hyrule.domain

import com.carles.hyrule.di.HyruleRepositoryQualifier
import com.carles.common.domain.AppSchedulers
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.HyruleRepo
import io.reactivex.Single
import javax.inject.Inject

class GetMonsterDetail @Inject constructor(
    @HyruleRepositoryQualifier
    private val repository: HyruleRepo,
    private val schedulers: AppSchedulers
) {

    fun execute(id: Int): Single<MonsterDetail> {
        return repository.getMonsterDetail(id)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}