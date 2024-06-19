package com.gkuziel.digiteq_assignment.ui


data class MainViewState(
    val reversed: Boolean,
    val layoutManagerType: LayoutManagerType,
    val snapHelperType: SnapHelperType,
    var position: Int,
    val columnCount: Int,
    val rowCount: Int
)