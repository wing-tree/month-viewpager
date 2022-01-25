package com.wing.tree.month.viewpager.presentation.ui

import android.content.res.Resources
import kotlin.math.roundToInt

val Int.dp get() = (this / Resources.getSystem().displayMetrics.density).roundToInt()
val Int.px get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Float.dp get() = this / Resources.getSystem().displayMetrics.density
val Float.px get() = this * Resources.getSystem().displayMetrics.density