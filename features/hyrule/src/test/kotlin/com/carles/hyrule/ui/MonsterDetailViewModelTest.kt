package com.carles.hyrule.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.carles.common.ui.navigation.Screen
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.domain.GetMonsterDetail
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MonsterDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMonsterDetail: GetMonsterDetail = mockk()
    private val state = SavedStateHandle().apply {
        set(Screen.MonsterDetail.monsterId, "1")
    }
    private lateinit var viewModel: MonsterDetailViewModel

    @Test
    fun `given initialization, when monster is obtained successfully, then update monsterDetail properly`() {
        val monsterDetail = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
        every { getMonsterDetail.execute(any()) } returns Single.just(monsterDetail)

        viewModel = MonsterDetailViewModel(state, getMonsterDetail)

        verify { getMonsterDetail.execute(1) }
        assertTrue(viewModel.uiState.value == MonsterDetailUiState(monster = monsterDetail))
    }

    @Test
    fun `given initialization, when there is an error obtaining monster, then update monsterDetail properly`() {
        val message = "some error"
        every { getMonsterDetail.execute(any()) } returns Single.error(Exception(message))

        viewModel = MonsterDetailViewModel(state, getMonsterDetail)

        verify { getMonsterDetail.execute(1) }
        assertTrue(viewModel.uiState.value == MonsterDetailUiState(error = message))
    }
}