package com.gkuziel.digiteq_assignment.adapter

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import androidx.recyclerview.widget.RecyclerView


class DualRecyclerDragAndDropListener(
    private val recyclerViewId0: Int,
    private val recyclerViewId1: Int,
    private val itemViewId: Int,
) : OnDragListener {

    private var isDropped = false

    override fun onDrag(
        view: View,
        event: DragEvent
    ): Boolean {

        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true

            if (droppedOnRecyclerViewOrItem(view.id)) {
                val draggedView = event.localState as View
                val sourceAdapter = findSourceAdapter(draggedView)
                val sourcePosition = draggedView.tag as Int
                val draggedItem = sourceAdapter!!.removeItemAt(sourcePosition)

                val destinationAdapter = findDestinationAdapter(view)
                val destinationPosition = droppedOnRecyclerViewOrItem(view)
                destinationAdapter?.insertAt(draggedItem, destinationPosition)
            }
        }
        return true
    }

    private fun droppedOnRecyclerViewOrItem(id: Int) =
        id == recyclerViewId0 || id == recyclerViewId1 || id == itemViewId

    private fun droppedOnRecyclerViewOrItem(view: View): Int {
        return if (view.id == itemViewId) {
            view.tag as Int
        } else {
            0
        }
    }

    private fun findDestinationAdapter(droppedOnView: View): ItemAdapter? {
        val recyclerView = when (droppedOnView.id) {
            recyclerViewId0 -> droppedOnView.rootView.findViewById(recyclerViewId0)
            recyclerViewId1 -> droppedOnView.rootView.findViewById(recyclerViewId1)
            else -> droppedOnView.parent
        }
        return (recyclerView as RecyclerView).adapter as ItemAdapter?
    }

    private fun findSourceAdapter(draggedView: View): ItemAdapter? {
        val sourceRecyclerView = draggedView.parent as RecyclerView
        return sourceRecyclerView.adapter as ItemAdapter?
    }
}