package com.huseyinbulbul.marvelus.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huseyinbulbul.marvelus.R
import com.huseyinbulbul.marvelus.common.data.Character
import com.huseyinbulbul.marvelus.databinding.ItemHeroBinding
import com.squareup.picasso.Picasso

class HeroAdapter(): ListAdapter<Character, HeroAdapter.HeroViewHolder>(HeroDiffCallback()) {
    private var heroSelectedListener: HeroSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false))
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        currentList[position]?.let {
            holder.bindViewHolder(it, heroSelectedListener)
        }
    }

    fun setOnHeroSelectedListener(listener: HeroSelectedListener){
        heroSelectedListener = listener
    }

    class HeroViewHolder(v: View): RecyclerView.ViewHolder(v){
        fun bindViewHolder(hero: Character, listener: HeroSelectedListener?){
            val binding = DataBindingUtil.bind<ItemHeroBinding>(itemView)
            binding?.hero = hero

            listener?.let {
                itemView.setOnClickListener {_ ->
                    hero.id?.let {id ->
                        it.onHeroSelected(id)
                    }
                }
            } ?: run {
                itemView.setOnClickListener(null)
            }
        }
    }

    class HeroDiffCallback: DiffUtil.ItemCallback<Character>(){
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

    }

    interface HeroSelectedListener{
        fun onHeroSelected(heroId: Int)
    }
}