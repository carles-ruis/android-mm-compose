package com.carles.common.domain

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AppDispatchers @Inject constructor(
    val io: CoroutineContext,
    val ui: CoroutineContext,
    val default: CoroutineContext
)