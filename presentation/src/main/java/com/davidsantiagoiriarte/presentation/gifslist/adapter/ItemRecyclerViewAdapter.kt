package com.davidsantiagoiriarte.presentation.gifslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.presentation.databinding.GifListItemBinding
import com.davidsantiagoiriarte.presentation.gifslist.FavoriteItemClickListener

class ItemRecyclerViewAdapter(private val favoriteItemClickListener: FavoriteItemClickListener) :
    ListAdapter<Gif, ItemViewHolder>(GIFS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            GifListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, favoriteItemClickListener)
    }

    companion object {
        private val GIFS_COMPARATOR = object : DiffUtil.ItemCallback<Gif>() {
            override fun areItemsTheSame(old: Gif, aNew: Gif): Boolean =
                old.id == aNew.id

            override fun areContentsTheSame(
                old: Gif,
                aNew: Gif
            ): Boolean =
                old == aNew
        }
    }
}
