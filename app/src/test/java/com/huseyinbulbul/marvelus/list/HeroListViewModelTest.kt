package com.huseyinbulbul.marvelus.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HeroListViewModelTest {
    @get:Rule
    val executionRule = InstantTaskExecutorRule()
    lateinit var manager: HeroManager
    lateinit var viewModel: HeroListViewModel

    @BeforeEach
    fun setup(){
        manager = mockk<HeroManager>(relaxed = true)
        viewModel = HeroListViewModel(manager)
    }

    @Test
    fun onStopTest(){
        viewModel.onStop()

        verify {
            manager.onStop()
        }
    }


}