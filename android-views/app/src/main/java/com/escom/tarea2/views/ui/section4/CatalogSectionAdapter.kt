package com.escom.tarea2.views.ui.section4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.escom.tarea2.views.data.SectionItem
import com.escom.tarea2.views.databinding.ItemCatalogBinding
import com.escom.tarea2.views.databinding.ItemSectionHeaderBinding

class CatalogSectionAdapter(
    private val items: List<SectionItem>,
    private val onItemClick: (SectionItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isHeader) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            val binding = ItemSectionHeaderBinding.inflate(inflater, parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemCatalogBinding.inflate(inflater, parent, false)
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is HeaderViewHolder) {
            holder.binding.tvSectionHeaderTitle.text = item.title
        } else if (holder is ItemViewHolder) {
            holder.binding.tvItemTitle.text = item.title
            holder.binding.tvItemDescription.text = item.description
            holder.binding.chipCategory.text = item.badge
            holder.itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(val binding: ItemSectionHeaderBinding) : RecyclerView.ViewHolder(binding.root)
    class ItemViewHolder(val binding: ItemCatalogBinding) : RecyclerView.ViewHolder(binding.root)
}
