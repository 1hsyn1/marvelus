package com.huseyinbulbul.marvelus.common.managers

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Analytics integration usually starts with choosing an analytics service provider
 * after that I would have implemented a singleton class like this and implement sending analytics event in here
 * this way it is easier to change one service to another, less error prone, easier to use
 */
@Singleton
class AnalyticsManager @Inject constructor(){
    companion object{
        const val OPEN_HERO = "open_hero_detail"
        const val GETTING_HEREOS = "getting_hereos"
    }

    fun sendEvent(eventName: String, data: Any?){

    }
}