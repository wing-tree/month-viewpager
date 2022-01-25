package com.wing.tree.month.viewpager.data.model

import android.icu.util.Calendar
import com.wing.tree.month.viewpager.constant.DAYS_PER_WEEK

data class Week(
    val year: Int,
    val month: Month,
    val weekOfMonth: Int,
) {
    private val firstDayOfWeek: Day get() = days[0]!!
    private val lastDayOfWeek: Day get() = days[DAYS_PER_WEEK.dec()]!!

    val days: LinkedHashMap<Int, Day> = linkedMapOf()

    init {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month.month)
            set(Calendar.WEEK_OF_MONTH, weekOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        repeat(DAYS_PER_WEEK) {
            val month = calendar.get(Calendar.MONTH)

            days[it] = Day.from(
                calendar, type = when {
                    month < this.month.month -> Month.Type.Previous
                    month > this.month.month -> Month.Type.Next
                    else -> Month.Type.This
                }
            )

            calendar.add(Calendar.DATE, 1)
        }
    }

    fun setInstances(instances: List<Instance>) {
        val linkedHashMap = linkedMapOf<Int, MutableList<Instance?>>()

        instances.forEachIndexed { _, instance ->
            val startDay = if (instance.startDay < firstDayOfWeek.julianDay) {
                if (instance.isAllDay) return@forEachIndexed
                firstDayOfWeek.julianDay
            } else {
                instance.startDay
            }

            val endDay = if (instance.endDay > lastDayOfWeek.julianDay) {
                lastDayOfWeek.julianDay
            } else {
                instance.endDay
            }

            linkedHashMap[startDay]?.add(instance) ?: run {
                linkedHashMap[startDay] = mutableListOf(instance)
            }

            instance.spanCount = endDay - startDay + 1
            instance.type = when {
                endDay < month.firstDayOfMonth.julianDay -> Month.Type.Previous
                startDay > month.lastDayOfMonth.julianDay -> Month.Type.Next
                else -> Month.Type.This
            }

            repeat(endDay - startDay) {
                val key = startDay + it.inc()

                if (key > lastDayOfWeek.julianDay) return@repeat

                linkedHashMap[key]?.add(null) ?: run {
                    linkedHashMap[key] = mutableListOf(null)
                }
            }
        }

        val offset = firstDayOfWeek.julianDay

        repeat(DAYS_PER_WEEK) {
            days[it]?.setInstances(linkedHashMap[it + offset] ?: emptyList())
        }
    }
}