package com.gkuziel.digiteq_assignment.utils

import android.graphics.Color

class ColorUtil {
    companion object {
        fun random() = Color.argb(255, random255(), random255(), random255())
        private fun random255() = (Math.random() * 255).toInt()
    }
}