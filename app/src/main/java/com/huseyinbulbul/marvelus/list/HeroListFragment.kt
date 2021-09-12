package com.huseyinbulbul.marvelus.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huseyinbulbul.marvelus.databinding.FragmentHeroListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListFragment: Fragment() {
    companion object{
        fun newInstance(): HeroListFragment{
            return HeroListFragment()
        }
    }

    lateinit var views: FragmentHeroListBinding
    lateinit var viewModel: HeroListViewModel

    private var adapter = HeroAdapter()
    private var onHeroSelectedListener: HeroAdapter.HeroSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HeroListViewModel::class.java)
        views = FragmentHeroListBinding.inflate(LayoutInflater.from(context))
        views.lifecycleOwner = requireActivity()
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnHeroSelectedListener(object: HeroAdapter.HeroSelectedListener{
            override fun onHeroSelected(heroId: Int) {
                onHeroSelectedListener?.let {
                    it.onHeroSelected(heroId)
                }
            }
        })

        views.rvHereos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        views.rvHereos.adapter = adapter
        views.rvHereos.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as? LinearLayoutManager)?.apply {
                    viewModel.scrolled(findLastVisibleItemPosition())
                }
            }
        })

        viewModel.isLoading.observe(requireActivity()){
            if(it){
                views.rlLoading.visibility = View.VISIBLE
            }else {
                views.rlLoading.visibility = View.GONE
            }
        }

        viewModel.listToShow.observe(requireActivity()){list ->
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        }

        viewModel.getHereos()
    }

    fun setOnHeroSelectedListener(listener: HeroAdapter.HeroSelectedListener){
        onHeroSelectedListener = listener
    }
}