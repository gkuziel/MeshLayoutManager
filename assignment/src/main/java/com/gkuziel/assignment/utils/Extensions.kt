package com.gkuziel.assignment.utils

import kotlin.math.max
import kotlin.math.min

fun Int.limited(min: Int, max: Int) = max(min, min(max, this))