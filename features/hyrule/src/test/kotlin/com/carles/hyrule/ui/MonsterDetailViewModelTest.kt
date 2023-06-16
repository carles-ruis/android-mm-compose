package com.carles.hyrule.ui

import androidx.lifecycle.SavedStateHandle
import com.carles.common.ui.navigation.Screen
import com.carles.commontest.MainDispatcherRule
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.domain.GetMonsterDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MonsterDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMonsterDetail: GetMonsterDetail = mockk()
    private val state = SavedStateHandle().apply {
        set(Screen.Arguments.monsterId, "1")
    }
    private lateinit var viewModel: MonsterDetailViewModel

    @Test
    fun `given initialization, when monster is obtained successfully, then update monsterDetail properly`() = runTest {
        val monsterDetail = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
        coEvery { getMonsterDetail.execute(any()) } returns monsterDetail

        viewModel = MonsterDetailViewModel(state, getMonsterDetail)

        coVerify { getMonsterDetail.execute(1) }
        assertTrue(viewModel.uiState.value == MonsterDetailUiState(monster = monsterDetail))
    }

    @Test
    fun `given initialization, when there is an error obtaining monster, then update monsterDetail properly`() = runTest {
        val message = "some error"
        coEvery { getMonsterDetail.execute(any()) } throws Exception(message)

        viewModel = MonsterDetailViewModel(state, getMonsterDetail)

        coVerify { getMonsterDetail.execute(1) }
        assertTrue(viewModel.uiState.value == MonsterDetailUiState(error = message))
    }
}