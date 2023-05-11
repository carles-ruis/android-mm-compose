package com.carles.hyrule.ui

import com.carles.commontest.MainDispatcherRule
import com.carles.hyrule.Monster
import com.carles.hyrule.domain.RefreshMonsters
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MonstersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val refreshMonsters: RefreshMonsters = mockk()
    private lateinit var viewModel: MonstersViewModel

    @Test
    fun `given initialization, when monsters are obtained successfully, then update ui properly`() = runTest {
        val monsters = listOf(Monster(1, "Monster"))
        coEvery { refreshMonsters.execute() } returns monsters

        viewModel = MonstersViewModel(refreshMonsters)

        coVerify { refreshMonsters.execute() }
        assertTrue(viewModel.uiState.value == MonstersUiState.Data(monsters = monsters))
    }

    @Test
    fun `given initialization, when there is an error obtaining monsters, then update ui properly`() = runTest {
        val message = "damned error"
        coEvery { refreshMonsters.execute() } throws Exception(message)

        viewModel = MonstersViewModel(refreshMonsters)

        coVerify { refreshMonsters.execute() }
        assertTrue(viewModel.uiState.value == MonstersUiState.Error(message = message))
    }

    @Test
    fun `given retry, when called, then refresh monsters`() = runTest {
        val monsters = listOf(Monster(1, "Monster"))
        coEvery { refreshMonsters.execute() } returns monsters

        viewModel = MonstersViewModel(refreshMonsters)
        viewModel.retry()

        coVerify(exactly = 2) { refreshMonsters.execute() }
    }
}