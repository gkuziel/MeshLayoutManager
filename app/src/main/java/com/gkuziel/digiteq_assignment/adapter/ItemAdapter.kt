package com.gkuziel.digiteq_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkuziel.digiteq_assignment.data.ItemViewModel
import com.gkuziel.digiteq_assignment.databinding.ItemNumberBinding


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private lateinit var items: MutableList<ItemViewModel>

    fun setItems(items: List<ItemViewModel>) {
        this.items = items.toMutableList()
        notifyItemRangeInserted(0, items.lastIndex)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemNumberBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            tvLabel.text = items[position].value.toString()
            root.setBackgroundColor(items[position].color)
        }
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(
        val binding: ItemNumberBinding
    ) : RecyclerView.ViewHolder(binding.root)
}