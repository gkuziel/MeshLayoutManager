package com.gkuziel.digiteq_assignment.ui


sealed class LayoutManagerType(val id: Int) {
    class Mesh : LayoutManagerType(1)
    class Linear : LayoutManagerType(2)
    class Grid : LayoutManagerType(3)

    companion object {
        fun getManager(id: Int): LayoutManagerType {
            return when (id) {
                1 -> Mesh()
                3 -> Linear()
                4 -> Grid()
                else -> {
                    throw Exception("invalid id")
                }
            }
        }
    }
}