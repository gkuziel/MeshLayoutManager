package com.gkuziel.assignment

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.gkuziel.assignment.utils.limited
import kotlin.math.abs


class MeshSnapHelper(
    private val columnCount: Int,
    private val rowCount: Int,
    private var isReversed: Boolean = false
) : SnapHelper() {

    private val pageSize by lazy { columnCount * rowCount }
    private var horizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val dx = distanceToParentEdge(layoutManager, targetView, getHorizontalHelper(layoutManager))
        return intArrayOf(dx, 0)
    }

    override fun findSnapView(
        layoutManager: RecyclerView.LayoutManager
    ) = findPageFirstView(layoutManager, getHorizontalHelper(layoutManager))

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val position = layoutManager.getPosition(snapView)
        if (position == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }
        val targetPosition = if (isPositionIncreasingWithScroll(velocityX)) {
            position + pageSize
        } else {
            position - pageSize
        }
        return targetPosition.limited(0, layoutManager.itemCount - 1)
    }

    private fun isPositionIncreasingWithScroll(
        velocityX: Int
    ) = if (isReversed) velocityX < 0 else velocityX > 0


    private fun distanceToParentEdge(
        layoutManager: RecyclerView.LayoutManager,
        view: View,
        helper: OrientationHelper
    ): Int {
        val distance = if (isReversed) {
            helper.getDecoratedEnd(view) - layoutManager.width
        } else {
            helper.getDecoratedStart(view) - helper.startAfterPadding
        }
        return distance
    }

    private fun findPageFirstView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        var closestChild: View? = null
        var absClosest = Int.MAX_VALUE

        for (i in 0 until layoutManager.childCount) {
            val child = layoutManager.getChildAt(i) ?: continue

            if (!isFirstItemInPage(layoutManager, child)) {
                continue
            }
            val absDistance = abs(distanceToParentEdge(layoutManager, child, helper))
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }

    private fun isFirstItemInPage(
        layoutManager: RecyclerView.LayoutManager,
        child: View?
    ) = child?.let {
        val positionInMesh = layoutManager.getPosition(it)
        positionInMesh % pageSize == 0
    } ?: false


    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ) = horizontalHelper ?: OrientationHelper.createHorizontalHelper(layoutManager)
        .also { horizontalHelper = it }
}
