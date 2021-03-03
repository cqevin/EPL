package com.chriskevin.epl.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.favorite.FavoriteAdapter.ViewHolder
import com.chriskevin.epl.favorite.databinding.FavoriteItemBinding
import com.chriskevin.epl.util.AdapterListener

class FavoriteAdapter(private val clickListener: AdapterListener<Int>) :
    ListAdapter<FavoriteTeam, ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<FavoriteTeam>() {
            override fun areItemsTheSame(oldItem: FavoriteTeam, newItem: FavoriteTeam): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteTeam,
                newItem: FavoriteTeam
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteTeam) {
            binding.favorite = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: AdapterListener<Int>): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteItemBinding.inflate(layoutInflater, parent, false)
                binding.clickListener = clickListener

                return ViewHolder(binding)
            }
        }
    }
}