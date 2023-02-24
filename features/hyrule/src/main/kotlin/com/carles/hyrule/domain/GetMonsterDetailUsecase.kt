package com.carles.hyrule.domain

import com.carles.common.domain.AppSchedulers
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.data.HyruleRepo
import io.reactivex.Single

class GetMonsterDetailUsecase(
    private val repository: HyruleRepo,
    private val schedulers: AppSchedulers
) {

    fun execute(id: Int): Single<MonsterDetail> {
        return repository.getMonsterDetail(id)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }
}