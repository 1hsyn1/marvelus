package com.huseyinbulbul.marvelus.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.common.data.Comic
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(private val heroManager: HeroManager): ViewModel() {
    var hero = MutableLiveData<Character>()
    var comics = MutableLiveData<List<Comic>>()
    var isLoading = MutableLiveData(false)

    fun onViewReady(heroId: Int){
        heroManager.getHeroById(heroId)?.let {
            hero.value = it
            isLoading.value = true
            it.getComics({list ->
                isLoading.value = false
                comics.value = list
            }, {
                isLoading.value = false
            })
        }
    }
}