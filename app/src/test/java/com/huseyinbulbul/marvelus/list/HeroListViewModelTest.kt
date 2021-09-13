package com.huseyinbulbul.marvelus.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.huseyinbulbul.marvelus.HeroViewModel
import com.huseyinbulbul.marvelus.RxImmediateSchedulerRule
import com.huseyinbulbul.marvelus.common.managers.AnalyticsManager
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HeroListViewModelTest {
    @get:Rule
    val executionRule = InstantTaskExecutorRule()
    @get:Rule
    val rxRule = RxImmediateSchedulerRule()
    lateinit var manager: HeroManager
    lateinit var analyticsManager: AnalyticsManager
    lateinit var viewModel: HeroListViewModel

    @Before
    fun setup(){
        manager = mockk<HeroManager>(relaxed = true)
        analyticsManager = mockk<AnalyticsManager>(relaxed = true)
        viewModel = HeroListViewModel(manager, analyticsManager)
    }

    @Test
    fun onStopTest(){
        viewModel.onStop()

        verify {
            manager.onStop()
        }
    }

    @Test
    fun `getHereos called when IsLoading true`(){
        viewModel.isLoading.value = true
        viewModel.getHereos()

        verify {
            manager wasNot Called
        }
    }

    @Test
    fun `getHereos called when IsLoading false`(){
        viewModel.isLoading.observeForever{}
        viewModel.isLoading.value = false
        viewModel.getHereos()

        verify {
            manager.getNext()
            analyticsManager.sendEvent(AnalyticsManager.GETTING_HEREOS, null)
        }
        assert(viewModel.isLoading.value == true)
    }

    @Test
    fun `scrolled when small lastVisibleItem`(){
        every { manager.getCurrentSize() } returns 20
        viewModel.scrolled(2)

        verify {
            manager.getNext() wasNot Called
        }
    }

    @Test
    fun `scrolled when big lastVisibleItem`(){
        every { manager.getCurrentSize() } returns 20
        viewModel.scrolled(12)

        verify {
            manager.getNext()
        }
    }

}