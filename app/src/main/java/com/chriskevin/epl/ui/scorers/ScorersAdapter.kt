package com.chriskevin.epl.ui.scorers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chriskevin.epl.core.domain.model.Scorers
import com.chriskevin.epl.databinding.ScorersItemBinding
import com.chriskevin.epl.ui.scorers.ScorersAdapter.ViewHolder
import com.chriskevin.epl.util.AdapterListener

class ScorersAdapter(private val clickListener: AdapterListener<Int>) :
    ListAdapter<Scorers, ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Scorers>() {
            override fun areItemsTheSame(oldItem: Scorers, newItem: Scorers): Boolean {
                return oldItem.player.id == newItem.player.id
            }

            override fun areContentsTheSame(
                oldItem: Scorers,
                newItem: Scorers
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

    class ViewHolder private constructor(private val binding: ScorersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Scorers) {
            binding.scorers = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: AdapterListener<Int>): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScorersItemBinding.inflate(layoutInflater, parent, false)
                binding.clickListener = clickListener

                return ViewHolder(binding)
            }
        }
    }
}