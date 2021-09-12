package com.huseyinbulbul.marvelus.common.managers

import android.util.Log
import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.common.network.ApiConnector
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroManager @Inject constructor(){
    private var offset = 0
    private var limit = 30
    private var hereos = mutableListOf<Character>()
    private var disposer = CompositeDisposable()

    fun getNext(): Observable<List<Character>>{
        val publisher = PublishSubject.create<List<Character>>()

        disposer.add(ApiConnector
            .getApi()
            .getCharacters(offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.results?.let { list ->
                    hereos.addAll(list)
                    offset += limit
                    publisher.onNext(hereos)
                } ?: run {
                    publisher.onError(Throwable("error"))
                }
            }, {
                publisher.onError(it)
            }))

        return publisher
    }

    fun onStop(){
        disposer.dispose()
    }

    fun getCurrentSize(): Int{
        return hereos.size
    }

    fun getHeroById(id: Int): Character?{
        hereos.forEach {
            if(id == it.id)
                return it
        }
        return null
    }
}