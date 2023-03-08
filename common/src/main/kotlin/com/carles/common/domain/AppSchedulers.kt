package com.carles.common.domain

import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Singleton

class AppSchedulers @Inject constructor(val io: Scheduler, val ui: Scheduler, val new: Scheduler)