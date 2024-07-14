package com.gkuziel.mesh_assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkuziel.mesh_assignment.R
import com.gkuziel.mesh_assignment.data.ItemViewModel
import com.gkuziel.mesh_assignment.databinding.ItemNumberBinding


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private lateinit var items: MutableList<ItemViewModel>

    fun setItems(items: MutableList<ItemViewModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.setDragListener()
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
            tvValue.text = items[position].value.toString()
            tvPosition.text = position.toString()
            root.setBackgroundColor(items[position].color)
            root.tag = position
            root.setOnLongClickListener { view ->
                val shadowBuilder = View.DragShadowBuilder(view)
                view.startDragAndDrop(null, shadowBuilder, view, 0)
            }
            root.setDragListener()
        }
    }

    fun insertAt(item: ItemViewModel, position: Int) {
        items.add(position, item)
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int): ItemViewModel {
        with(items.removeAt(position)) {
            notifyDataSetChanged()
            return this
        }
    }

    private fun View.setDragListener() {
        setOnDragListener(
            DualRecyclerDragAndDropListener(
                R.id.recyclerview_top,
                R.id.recyclerview_bottom,
                R.id.frame_root
            )
        )
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(
        val binding: ItemNumberBinding
    ) : RecyclerView.ViewHolder(binding.root)
}