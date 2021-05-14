package com.davidsantiagoiriarte.presentation.gifslist.adapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.presentation.R
import com.davidsantiagoiriarte.presentation.databinding.GifListItemBinding
import com.davidsantiagoiriarte.presentation.gifslist.FavoriteItemClickListener

/**
 * Created by david on 4/25/2021.
 * David Iriarte
 * davidsantiagoiriarte@gmail.com
 */
class ItemViewHolder(binding: GifListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title: TextView = binding.title
    private val gifImageView: ImageView = binding.ivGif
    private val favoriteIcon: ImageView = binding.ivFavorite

    fun bind(item: Gif, favoriteItemClickListener: FavoriteItemClickListener, position: Int) {
        title.text = item.title
        Glide.with(itemView.context)
            .load(item.getRealUrl())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(gifImageView)
        setFavoriteIcon(item.isFavorite)
        favoriteIcon.setOnClickListener {
            favoriteItemClickListener.onFavoriteClicked(item, position)
        }
        gifImageView.setOnClickListener {
            favoriteItemClickListener.onGifClicked(item)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
