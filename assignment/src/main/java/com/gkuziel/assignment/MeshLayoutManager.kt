package com.gkuziel.assignment

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class MeshLayoutManager(
    private val columnCount: Int,
    private val rowCount: Int,
    private val reversed: Boolean,
) : RecyclerView.LayoutManager() {


    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    )

    override fun canScrollVertically() = false
    override fun canScrollHorizontally() = true

    override fun onLayoutChildren(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ) {
        fill(recycler)
    }

    private fun fill(
        recycler: Recycler,
    ) {
        detachAndScrapAttachedViews(recycler)

        val pageSize = columnCount * rowCount
        val itemWidth = width / columnCount
        val itemHeight = height / rowCount

        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val left = calculateLeftCoordinate(i, pageSize, itemWidth)
            val top = calculateTopCoordinate(i, pageSize, itemHeight)

            layoutDecoratedWithMargins(
                view,
                left,
                top,
                left + itemWidth,
                top + itemHeight
            )
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    private fun calculateLeftCoordinate(
        index: Int,
        pageSize: Int,
        itemWidth: Int
    ) = ((index / pageSize) * columnCount + (index % columnCount)) * itemWidth

    private fun calculateTopCoordinate(
        index: Int,
        pageSize: Int,
        itemHeight: Int
    ) = ((index % pageSize) / columnCount) * itemHeight
}
