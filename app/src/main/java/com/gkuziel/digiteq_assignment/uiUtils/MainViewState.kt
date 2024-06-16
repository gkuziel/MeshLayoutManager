package com.gkuziel.digiteq_assignment.uiUtils


data class MainViewState(
    val reversed: Boolean,
    val layoutManagerType: LayoutManagerType,
    val snapHelperType: SnapHelperType,
    val position: Int?
)