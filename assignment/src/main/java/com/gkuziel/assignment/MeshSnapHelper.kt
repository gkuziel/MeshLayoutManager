package com.gkuziel.assignment

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min


class MeshSnapHelper(
    private val columnCount: Int,
    private val rowsCount: Int
) : PagerSnapHelper() {

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val position = layoutManager.getPosition(snapView)
        val step = rowsCount * columnCount

        val nextPosition = if (velocityX < 0) {
            position - step
        } else {
            position + step
        }
        val firstIndex = 0
        val lastIndex = layoutManager.itemCount - 1
        return min(lastIndex, max(nextPosition, firstIndex))
    }
}