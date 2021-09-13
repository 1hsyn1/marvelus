package com.huseyinbulbul.marvelus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.huseyinbulbul.marvelus.databinding.ActivityHeroBinding
import com.huseyinbulbul.marvelus.detail.HeroDetailFragment
import com.huseyinbulbul.marvelus.list.HeroAdapter
import com.huseyinbulbul.marvelus.list.HeroListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroActivity : AppCompatActivity() {
    lateinit var views: ActivityHeroBinding
    lateinit var viewModel: HeroViewModel
    lateinit var listFragment: HeroListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HeroViewModel::class.java)
        views = ActivityHeroBinding.inflate(LayoutInflater.from(this))
        setContentView(views.root)
        views.lifecycleOwner = this

        listFragment = HeroListFragment.newInstance()
        listFragment.setOnHeroSelectedListener(object: HeroAdapter.HeroSelectedListener{
            override fun onHeroSelected(heroId: Int) {
                viewModel.heroSelected(heroId)
            }
        })

        viewModel.heroId.observe(this){
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fl_fragment_container, HeroDetailFragment.newInstance(it))
                    .addToBackStack("stack")
                    .commit()
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_fragment_container, listFragment)
            .commit()
    }
}