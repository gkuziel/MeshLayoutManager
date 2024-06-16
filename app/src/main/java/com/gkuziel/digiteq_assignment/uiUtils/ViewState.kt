package com.gkuziel.digiteq_assignment.uiUtils


data class ViewState(
    val reversed: Boolean,
    val layoutManagerType: LayoutManagerType,
    val snapHelperType: SnapHelperType,
    val position: Int?
)