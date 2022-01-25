package com.wing.tree.month.viewpager.presentation.ui

import kotlin.math.roundToInt

object Alpha {
    val Float.decimal: Int
        get() = (this * 255.0F).roundToInt()
}