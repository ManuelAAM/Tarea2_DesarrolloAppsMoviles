package com.escom.tarea2.views.ui.section4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.escom.tarea2.views.data.CatalogItem
import com.escom.tarea2.views.databinding.ItemCatalogGridBinding

class CatalogGridAdapter(
    private var items: List<CatalogItem>,
    private val onItemClick: (CatalogItem) -> Unit
) : RecyclerView.Adapter<CatalogGridAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCatalogGridBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatalogGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvGridTitle.text = item.title
        holder.binding.tvGridCategory.text = item.category

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<CatalogItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
