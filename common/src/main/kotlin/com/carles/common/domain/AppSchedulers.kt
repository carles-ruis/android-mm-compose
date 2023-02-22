package com.carles.common.domain

import io.reactivex.Scheduler

class AppSchedulers(val io: Scheduler, val ui: Scheduler, val new: Scheduler)