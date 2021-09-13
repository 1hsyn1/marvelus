package com.huseyinbulbul.marvelus

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinbulbul.marvelus.common.managers.AnalyticsManager
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HeroViewModelTest {
    @get:Rule
    val executionRule = InstantTaskExecutorRule()
    @get:Rule
    val rxRule = RxImmediateSchedulerRule()
    lateinit var analyticsManager: AnalyticsManager
    lateinit var viewModel: HeroViewModel

    @Before
    fun setup(){
        analyticsManager = mockk(relaxed = true)
        viewModel = HeroViewModel(analyticsManager)
    }

    @Test
    fun `heroSelected with wrong heroId`(){
        viewModel.heroId.observeForever {}
        viewModel.heroSelected(-1)

        verify {
            analyticsManager wasNot Called
        }
    }

    @Test
    fun `heroSelected with valid heroId`(){
        val heroId = 1
        viewModel.heroId.observeForever {}
        viewModel.heroSelected(heroId)

        verify {
            analyticsManager.sendEvent(AnalyticsManager.OPEN_HERO, heroId)
        }
        assert(viewModel.heroId.value == heroId)
    }
}