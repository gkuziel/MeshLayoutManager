package com.gkuziel.digiteq_assignment.data

import android.graphics.Color

class Data {

    companion object {
        val numbers = List(50) {
            ItemViewModel(it)
        }

        val colors = List(50) {
            Color.argb(255, random256(), random256(), random256())
        }

        private fun random256() = (Math.random() * 255).toInt()
    }
}