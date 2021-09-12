package com.huseyinbulbul.marvelus.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinbulbul.marvelus.databinding.FragmentHeroDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment: Fragment() {
    companion object{
        fun newInstance(heroId: Int): HeroDetailFragment{
            val fragment = HeroDetailFragment()
            fragment.heroId = heroId
            return fragment
        }
    }

    lateinit var views: FragmentHeroDetailBinding
    lateinit var viewModel: HeroDetailViewModel

    private val adapter = ComicAdapter()
    private var heroId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HeroDetailViewModel::class.java)
        views = FragmentHeroDetailBinding.inflate(LayoutInflater.from(requireContext()))
        views.lifecycleOwner = this
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(heroId == -1) {
            requireActivity().onBackPressed()
            return
        }

        views.rvComics.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        views.rvComics.adapter = adapter

        viewModel.isLoading.observe(requireActivity()){
            views.rlLoading.visibility = if(it) View.VISIBLE else View.GONE
        }
        6
        viewModel.hero.observe(requireActivity()){
            views.hero = it
        }

        viewModel.comics.observe(requireActivity()){
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        views.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.onViewReady(heroId)
    }
}