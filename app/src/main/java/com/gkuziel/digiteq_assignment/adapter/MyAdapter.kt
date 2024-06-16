package com.gkuziel.digiteq_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkuziel.digiteq_assignment.data.Data
import com.gkuziel.digiteq_assignment.data.ItemViewModel
import com.gkuziel.digiteq_assignment.databinding.ItemNumberBinding


class MyAdapter(
    private val list: List<ItemViewModel>
) : RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {

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
            tvLabel.text = list[position].value.toString()
            root.setBackgroundColor(Data.colors[position])
        }
    }

    override fun getItemCount() = list.size

    inner class ItemViewHolder(
        val binding: ItemNumberBinding
    ) : RecyclerView.ViewHolder(binding.root)
}