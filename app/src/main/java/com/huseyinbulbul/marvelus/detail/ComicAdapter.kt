package com.huseyinbulbul.marvelus.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huseyinbulbul.marvelus.R
import com.huseyinbulbul.marvelus.common.data.Comic
import com.huseyinbulbul.marvelus.databinding.ItemComicBinding

class ComicAdapter(): ListAdapter<Comic, ComicAdapter.ComicViewHolder>(ComicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false))
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        currentList[position]?.let {
            holder.bindViewHolder(it)
        }
    }

    class ComicViewHolder(v: View): RecyclerView.ViewHolder(v){
        fun bindViewHolder(comic: Comic){
            val binding = DataBindingUtil.bind<ItemComicBinding>(itemView)
            binding?.comic = comic
        }
    }

    class ComicDiffCallback: DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.title == newItem.title
        }

    }
}