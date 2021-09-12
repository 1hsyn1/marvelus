package com.huseyinbulbul.marvelus.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinbulbul.marvelus.databinding.ActivityHeroListBinding
import com.huseyinbulbul.marvelus.detail.HeroDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListActivity : AppCompatActivity() {
    lateinit var views: ActivityHeroListBinding
    lateinit var viewModel: HeroListViewModel

    private var adapter = HeroAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HeroListViewModel::class.java)
        views = ActivityHeroListBinding.inflate(LayoutInflater.from(this))
        setContentView(views.root)
        views.lifecycleOwner = this



        adapter.setOnHeroSelectedListener(object: HeroAdapter.HeroSelectedListener{
            override fun onHeroSelected(heroId: Int) {
                val intent = Intent(this@HeroListActivity, HeroDetailActivity::class.java)
                intent.putExtra(HeroDetailActivity.HERO_ID, heroId)
                startActivity(intent)
            }
        })

        views.rvHereos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        views.rvHereos.adapter = adapter
        views.rvHereos.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as? LinearLayoutManager)?.apply {
                    viewModel.scrolled(findLastVisibleItemPosition())
                }
            }
        })

        viewModel.isLoading.observe(this){
            if(it){
                views.rlLoading.visibility = View.VISIBLE
            }else {
                views.rlLoading.visibility = View.GONE
            }
        }

        viewModel.listToShow.observe(this){list ->
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        }

        viewModel.getHereos()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }
}