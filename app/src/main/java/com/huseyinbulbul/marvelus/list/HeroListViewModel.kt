package com.huseyinbulbul.marvelus.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(private val manager: HeroManager): ViewModel() {
    private var disposer = CompositeDisposable()

    var isLoading = MutableLiveData(false)
    val listToShow = MutableLiveData<List<Character>>(listOf())

    fun getHereos(){
        if(isLoading.value == true)
            return

        isLoading.value = true
        disposer.add(manager.getNext()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoading.value = false
                listToShow.value = it
            }, {
                isLoading.value = false
            }))
    }

    fun onStop(){
        disposer.dispose()
        manager.onStop()
    }

    fun scrolled(lastVisibleItem: Int){
        if(lastVisibleItem + 10 >= manager.getCurrentSize() && isLoading.value == false){
            getHereos()
        }
    }
}