package com.huseyinbulbul.marvelus.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HeroDetailViewModelTest {
    @get:Rule
    val executionRule = InstantTaskExecutorRule()
    lateinit var manager: HeroManager
    lateinit var viewModel: HeroDetailViewModel

    @Before
    fun setup(){
        manager = mockk<HeroManager>(relaxed = true)
        viewModel = HeroDetailViewModel(manager)
    }

    @Test
    fun `onViewReady with wrong heroId`(){
        viewModel.isLoading.observeForever{}
        viewModel.hero.observeForever{}
        every { manager.getHeroById(any()) } returns null
        viewModel.onViewReady(1)

        assert(viewModel.isLoading.value == false)
        assert(viewModel.hero.value == null)
    }

    @Test
    fun `onViewReady with working heroId`(){
        val hero = mockk<Character>(relaxed = true)

        viewModel.isLoading.observeForever{}
        viewModel.hero.observeForever{}
        every { manager.getHeroById(any()) } returns hero
        viewModel.onViewReady(1)

        assert(viewModel.isLoading.value == true)
        assert(viewModel.hero.value == hero)
        verify {
            hero.getComics(any(), any())
        }
    }

}