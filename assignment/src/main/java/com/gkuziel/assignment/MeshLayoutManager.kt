package com.gkuziel.assignment

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class MeshLayoutManager(
    private val columnCount: Int,
    private val rowCount: Int,
    private val reversed: Boolean,
) : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = 0
    private val pageCount by lazy { ceil(itemCount.toDouble() / (columnCount * rowCount)).toInt() }
    private val pageSize by lazy { columnCount * rowCount }
    private val itemWidth by lazy { width / columnCount }
    private val itemHeight by lazy { height / rowCount }


    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    )

    override fun canScrollVertically() = false

    override fun canScrollHorizontally() = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val minPosition = 0
        val maxPosition = (pageCount - 1) * width
        horizontalScrollOffset = min(maxPosition, max(minPosition, dx + horizontalScrollOffset))
        fill(recycler)
        return if (isScrolledToEdge(maxPosition)) {
            0
        } else {
            dx
        }
    }

    private fun isScrolledToEdge(maxPosition: Int) =
        horizontalScrollOffset == 0 || horizontalScrollOffset == maxPosition

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
    ) =
        ((index / pageSize) * columnCount + (index % columnCount)) * itemWidth - horizontalScrollOffset

    private fun calculateTopCoordinate(
        index: Int,
        pageSize: Int,
        itemHeight: Int
    ) = ((index % pageSize) / columnCount) * itemHeight
}
