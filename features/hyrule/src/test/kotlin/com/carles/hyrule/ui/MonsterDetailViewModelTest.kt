package com.carles.hyrule.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carles.common.ui.ERROR
import com.carles.common.ui.SUCCESS
import com.carles.hyrule.MonsterDetail
import com.carles.hyrule.domain.GetMonsterDetail
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MonsterDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMonsterDetail: GetMonsterDetail = mockk()
    private val id = 1
    private lateinit var viewModel: MonsterDetailViewModel

    @Test
    fun `given initialization, when monster is obtained successfully, then update monsterDetail properly`() {
        val monsterDetail = MonsterDetail(1, "Monster", "here", "big monster", "https://url")
        every { getMonsterDetail.execute(any()) } returns Single.just(monsterDetail)

        viewModel = MonsterDetailViewModel(id, getMonsterDetail)

        verify { getMonsterDetail.execute(1) }
        val result = viewModel.monsterDetail.value!!
        assertTrue(result.state is SUCCESS)
        assertEquals(result.data, monsterDetail)
        assertNull(result.message)
    }

    @Test
    fun `given initialization, when there is an error obtaining monster, then update monsterDetail properly`() {
        val message = "some error"
        every { getMonsterDetail.execute(any()) } returns Single.error(Exception(message))

        viewModel = MonsterDetailViewModel(id, getMonsterDetail)

        verify { getMonsterDetail.execute(1) }
        val result = viewModel.monsterDetail.value!!
        assertTrue(result.state is ERROR)
        assertNull(result.data)
        assertEquals(result.message, message)
    }
}