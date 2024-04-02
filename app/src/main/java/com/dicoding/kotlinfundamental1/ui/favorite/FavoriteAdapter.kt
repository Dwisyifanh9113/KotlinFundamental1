package com.dicoding.kotlinfundamental1.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.kotlinfundamental1.data.local.FavoriteEntity
import com.dicoding.kotlinfundamental1.databinding.ItemFavoritesBinding

class FavoriteAdapter(
    private val onClick: (FavoriteEntity) -> Unit,
) : ListAdapter<FavoriteEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    class MyViewHolder(val binding: ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteEntity) {
            binding.name.text = user.username
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.profile)
                .clearOnDetach()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}