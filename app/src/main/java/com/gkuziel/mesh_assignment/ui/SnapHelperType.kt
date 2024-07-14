package com.gkuziel.mesh_assignment.ui


sealed class SnapHelperType(val id: Int) {
    class Mesh : SnapHelperType(1)
    class Linear : SnapHelperType(2)
    class Pager : SnapHelperType(3)
    class None : SnapHelperType(4)

    companion object {
        fun getHelper(id: Int): SnapHelperType {
            return when (id) {
                1 -> Mesh()
                2 -> Linear()
                3 -> Pager()
                4 -> None()
                else -> throw Exception("invalid id")
            }
        }
    }
}