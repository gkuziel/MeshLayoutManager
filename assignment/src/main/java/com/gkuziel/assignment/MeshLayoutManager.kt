package com.gkuziel.assignment

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class MeshLayoutManager(
    private val context: Context,
    private val columnCount: Int,
    private val rowCount: Int,
    private val reversed: Boolean,
) : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset: Int = -1
        get() = synchronized(this) {
            return if (field == -1) {
                if (reversed) {
                    maxScroll
                } else {
                    0
                }
            } else field
        }


    private val pageCount
        get() = ceil(itemCount.toDouble() / (columnCount * rowCount)).toInt()

    private val maxScroll
        get() = (pageCount - 1) * width

    private val pageSize by lazy { columnCount * rowCount }
    private val itemWidth by lazy { width / columnCount }
    private val itemHeight by lazy { height / rowCount }

    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    )

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return getChildAt(0)?.let {
                    val firstChildPosition = getPosition(it)
                    val direction = if (targetPosition < firstChildPosition != reversed) -1 else 1
                    PointF(direction.toFloat(), 0f)
                }
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    override fun scrollToPosition(position: Int) {
        val lastPosition = horizontalScrollOffset
        val destinationPosition = calculateLeftCoordinate(position) + lastPosition

        horizontalScrollOffset = if (lastPosition > destinationPosition) {
            destinationPosition.limited(0, maxScroll)
        } else {
            val directionOffset = itemWidth * (columnCount - 1)
            (destinationPosition - directionOffset).limited(0, maxScroll)
        }
        requestLayout()
    }

    override fun canScrollVertically() = false

    override fun canScrollHorizontally() = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val minScroll = 0
        horizontalScrollOffset = (dx + horizontalScrollOffset).limited(minScroll, maxScroll)
        fill(recycler)
        return if (isScrolledToEdge(maxScroll)) {
            0
        } else {
            dx
        }
    }

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

        val firstVisiblePosition = firstVisiblePosition()
        val lastVisiblePosition = lastVisiblePosition(firstVisiblePosition)

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val left = calculateLeftCoordinate(i)
            val top = calculateTopCoordinate(i)

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

    private fun firstVisiblePosition(): Int {
        return if (reversed) {
            val reverseOffset = (pageCount - 1) * width
            val firstVisiblePage = (reverseOffset - horizontalScrollOffset) / width
            val firstVisiblePositionInPage =
                (reverseOffset - horizontalScrollOffset) % width / itemWidth
            firstVisiblePage * pageSize + firstVisiblePositionInPage

        } else {
            val firstVisiblePage = horizontalScrollOffset / width
            val firstVisiblePositionInPage = horizontalScrollOffset % width / itemWidth
            firstVisiblePage * pageSize + firstVisiblePositionInPage
        }
    }

    private fun lastVisiblePosition(firstVisiblePosition: Int) =
        min(firstVisiblePosition + pageSize + (rowCount - 1) * columnCount, itemCount - 1)


    private fun calculateLeftCoordinate(index: Int): Int {
        val left =
            ((index / pageSize) * columnCount + (index % columnCount)) * itemWidth
        return if (reversed) {
            pageCount * width - itemWidth - left - horizontalScrollOffset
        } else {
            left - horizontalScrollOffset
        }
    }

    private fun calculateTopCoordinate(index: Int) = ((index % pageSize) / columnCount) * itemHeight

    private fun isScrolledToEdge(maxPosition: Int) =
        horizontalScrollOffset == 0 || horizontalScrollOffset == maxPosition

    private fun Int.limited(min: Int, max: Int) = max(min, min(max, this))
}