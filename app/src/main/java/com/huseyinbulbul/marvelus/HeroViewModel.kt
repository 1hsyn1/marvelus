package com.huseyinbulbul.marvelus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyinbulbul.marvelus.common.managers.AnalyticsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(private val analyticsManager: AnalyticsManager) : ViewModel() {
    val heroId = MutableLiveData<Int>()

    fun heroSelected(heroId: Int){
        if(heroId > -1){
            analyticsManager.sendEvent(AnalyticsManager.OPEN_HERO, heroId)
            this.heroId.value = heroId
        }
    }
}