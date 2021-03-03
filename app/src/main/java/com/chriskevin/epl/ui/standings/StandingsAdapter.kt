package com.chriskevin.epl.ui.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chriskevin.epl.R
import com.chriskevin.epl.core.domain.model.Table
import com.chriskevin.epl.databinding.StandingsItemBinding
import com.chriskevin.epl.util.AdapterListener
import com.chriskevin.epl.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StandingsAdapter(private val clickListener: AdapterListener<Int>) :
    ListAdapter<StandingsDataItem, RecyclerView.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StandingsDataItem>() {
            override fun areItemsTheSame(oldItem: StandingsDataItem, newItem: StandingsDataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StandingsDataItem,
                newItem: StandingsDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Table>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(StandingsDataItem.Header)
                else -> listOf(StandingsDataItem.Header) + list.map { StandingsDataItem.TableItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is StandingsDataItem.Header -> Const.ITEM_VIEW_TYPE_HEADER
            is StandingsDataItem.TableItem -> Const.ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            Const.ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent, clickListener)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val tableItem = getItem(position) as StandingsDataItem.TableItem
                holder.bind(tableItem.table)
            }
        }
    }

    class ViewHolder private constructor(private val binding: StandingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Table) {
            binding.table = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: AdapterListener<Int>): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StandingsItemBinding.inflate(layoutInflater, parent, false)
                binding.clickListener = clickListener

                return ViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.standings_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }
}

sealed class StandingsDataItem {
    data class TableItem(val table: Table) : StandingsDataItem() {
        override val id: Int = table.team.id
    }

    object Header : StandingsDataItem() {
        override val id: Int = Int.MIN_VALUE
    }

    abstract val id: Int
}