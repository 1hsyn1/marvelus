package com.huseyinbulbul.marvelus.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinbulbul.marvelus.R
import com.huseyinbulbul.marvelus.common.managers.HeroManager
import com.huseyinbulbul.marvelus.databinding.ActivityHeroDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailActivity : AppCompatActivity() {
    companion object{
        const val HERO_ID = "hero_id"
    }
    lateinit var views: ActivityHeroDetailBinding
    lateinit var viewModel: HeroDetailViewModel

    private val adapter = ComicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HeroDetailViewModel::class.java)
        views = ActivityHeroDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(views.root)
        views.lifecycleOwner = this

        val heroId = intent.getIntExtra(HERO_ID, 0)
        if(heroId == 0) {
            finish()
            return
        }

        views.rvComics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        views.rvComics.adapter = adapter

        viewModel.isLoading.observe(this){
            views.rlLoading.visibility = if(it) View.VISIBLE else View.GONE
        }
 6
        viewModel.hero.observe(this){
            views.hero = it
        }

        viewModel.comics.observe(this){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        views.ivBack.setOnClickListener {
            finish()
        }

        viewModel.onViewReady(heroId)
    }
}