package com.wing.tree.month.viewpager.util

internal val Any?.isNull: Boolean get() = this == null
internal val Any?.notNull: Boolean get() = isNull.not()
internal val Double.float: Float get() = this.toFloat()
internal val Int.double: Double get() = this.toDouble()
internal val Int.float: Float get() = this.toFloat()