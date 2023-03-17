package com.carles.hyrule.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carles.hyrule.Monster
import com.carles.hyrule.domain.RefreshMonsters
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MonstersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val refreshMonsters: RefreshMonsters = mockk()
    private lateinit var viewModel: MonstersViewModel

    @Test
    fun `given initialization, when monsters are obtained successfully, then update ui properly`() {
        val monsters = listOf(Monster(1, "Monster"))
        every { refreshMonsters.execute() } returns Single.just(monsters)

        viewModel = MonstersViewModel(refreshMonsters)

        verify { refreshMonsters.execute() }
        assertTrue(viewModel.uiState.value == MonstersUiState.Data(monsters = monsters))
    }

    @Test
    fun `given initialization, when there is an error obtaining monsters, then update ui properly`() {
        val message = "damned error"
        every { refreshMonsters.execute() } returns Single.error(Exception(message))

        viewModel = MonstersViewModel(refreshMonsters)

        verify { refreshMonsters.execute() }
        assertTrue(viewModel.uiState.value == MonstersUiState.Error(message = message))
    }

    @Test
    fun `given retry, when called, then refresh monsters`() {
        val monsters = listOf(Monster(1, "Monster"))
        every { refreshMonsters.execute() } returns Single.just(monsters)

        viewModel = MonstersViewModel(refreshMonsters)
        viewModel.retry()

        verify(exactly = 2) { refreshMonsters.execute() }
    }
}