package com.wing.tree.month.viewpager.data.model

import android.icu.util.Calendar
import com.wing.tree.month.viewpager.constant.WEEKS_PER_MONTH

data class Month(
    val year: Int,
    val month: Int
) {
    val firstDayOfMonth: Day
    val lastDayOfMonth: Day
    val weeks = linkedMapOf<Int, Week>()

    init {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            set(Calendar.DAY_OF_MONTH, getActualMinimum(Calendar.DAY_OF_MONTH))

            firstDayOfMonth = Day.from(this, Type.This)

            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))

            lastDayOfMonth = Day.from(this, Type.This)
        }

        repeat(WEEKS_PER_MONTH) {
            weeks[it] = Week(
                year = year,
                month = this,
                weekOfMonth = it.inc()
            )
        }
    }

    fun setInstances(instances: LinkedHashMap<Int, List<Instance>>) {
        instances.forEach {
            weeks[it.key]?.setInstances(it.value)
        }
    }

    enum class Type {
        Previous, This, Next
    }
}