package com.davidsantiagoiriarte.presentation.gifslist.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    fun bind(item: Gif, favoriteItemClickListener: FavoriteItemClickListener) {
        title.text = item.title
        Glide.with(itemView.context).asGif().load(item.gifUrl).into(gifImageView)
        if (item.isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border)
        }
        favoriteIcon.setOnClickListener {
            favoriteItemClickListener.onFavoriteClicked(item)
        }
    }
}
