package com.carles.hyrule.domain

import com.carles.common.domain.AppDispatchers
import com.carles.hyrule.Monster
import com.carles.hyrule.data.HyruleRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RefreshMonstersTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val dispatchers = AppDispatchers(dispatcher, dispatcher, dispatcher)
    private val repository: HyruleRepo = mockk()
    private lateinit var usecase: RefreshMonsters

    @Before
    fun setup() {
        usecase = RefreshMonsters(repository, dispatchers)
    }

    @Test
    fun `given usecase, when called, then refresh monsters from repository`() = runTest {
        val monsters = listOf(Monster(1, "Monster"))
        coEvery { repository.refreshMonsters() } returns monsters
        assertEquals(monsters, usecase.execute())
        coVerify { repository.refreshMonsters() }
    }
}