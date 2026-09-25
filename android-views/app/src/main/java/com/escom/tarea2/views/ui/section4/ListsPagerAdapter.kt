package com.escom.tarea2.views.ui.section4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.escom.tarea2.views.databinding.ItemCatalogBinding

class ListsPagerAdapter(
    private val pages: List<Pair<String, String>>
) : RecyclerView.Adapter<ListsPagerAdapter.PageViewHolder>() {

    class PageViewHolder(val binding: ItemCatalogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ItemCatalogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val (title, description) = pages[position]
        holder.binding.tvItemTitle.text = title
        holder.binding.tvItemDescription.text = description
        holder.binding.chipCategory.text = "Pestaña Deslizable ${position + 1} de ${pages.size}"
    }

    override fun getItemCount(): Int = pages.size
}
