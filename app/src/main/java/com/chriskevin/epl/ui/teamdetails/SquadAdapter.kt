package com.chriskevin.epl.ui.teamdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chriskevin.epl.core.domain.model.TeamSquad
import com.chriskevin.epl.databinding.SquadItemBinding
import com.chriskevin.epl.ui.teamdetails.SquadAdapter.ViewHolder

class SquadAdapter : ListAdapter<TeamSquad, ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TeamSquad>() {
            override fun areItemsTheSame(oldItem: TeamSquad, newItem: TeamSquad): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TeamSquad,
                newItem: TeamSquad
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: SquadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TeamSquad) {
            binding.squad = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SquadItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}