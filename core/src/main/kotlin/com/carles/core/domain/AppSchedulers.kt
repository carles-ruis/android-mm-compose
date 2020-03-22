package com.carles.core.domain

import io.reactivex.Scheduler

class AppSchedulers(val io: Scheduler, val ui: Scheduler, val new: Scheduler)