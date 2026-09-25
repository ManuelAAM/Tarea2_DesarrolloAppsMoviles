package com.escom.tarea2.views.ui.section4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.escom.tarea2.views.data.CatalogItem
import com.escom.tarea2.views.databinding.ItemCatalogBinding

class CatalogListAdapter(
    private var items: MutableList<CatalogItem>,
    private val onItemClick: (CatalogItem) -> Unit
) : RecyclerView.Adapter<CatalogListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCatalogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatalogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvItemTitle.text = item.title
        holder.binding.tvItemDescription.text = item.description
        holder.binding.chipCategory.text = item.category

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<CatalogItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): CatalogItem = items[position]

    fun removeItemAt(position: Int): CatalogItem {
        val item = items.removeAt(position)
        notifyItemRemoved(position)
        return item
    }
}
