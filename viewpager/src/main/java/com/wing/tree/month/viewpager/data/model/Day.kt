package com.wing.tree.month.viewpager.data.model

import android.icu.util.Calendar

data class Day (
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeek: Int,
    val julianDay: Int,
    val type: Month.Type
) {
    val instances = mutableListOf<Instance?>()

    fun setInstances(instances: List<Instance?>) {
        this.instances.addAll(instances)
    }

    companion object {
        val today: Day get() = from(Calendar.getInstance(), Month.Type.This)

        fun from(calendar: Calendar, type: Month.Type) = Day(
            month = calendar.get(Calendar.MONTH),
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH),
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK),
            julianDay = calendar.get(Calendar.JULIAN_DAY),
            type = type
        )
    }
}