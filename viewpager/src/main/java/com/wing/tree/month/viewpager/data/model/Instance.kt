package com.wing.tree.month.viewpager.data.model

import android.database.Cursor
import androidx.annotation.ColorInt
import com.wing.tree.month.viewpager.data.query.parameter.Instances

data class Instance(
    val allDay: Int,
    val begin: Long,
    @ColorInt
    val calendarColor: Int,
    val calendarId: Long,
    val end: Long,
    val endDay: Int,
    @ColorInt
    val eventColor: Int,
    val eventId: Long,
    val id: Long,
    val startDay: Int,
    val title: String,
    var spanCount: Int = 0,
    var type: Month.Type = Month.Type.This
) {
    val isAllDay = allDay == 1

    companion object {
        fun from(cursor: Cursor): Instance {
            val allDay = cursor.getInt(Instances.Index.ALL_DAY)
            val begin = cursor.getLong(Instances.Index.BEGIN)
            val calendarColor = cursor.getInt(Instances.Index.CALENDAR_COLOR)
            val calendarId = cursor.getLong(Instances.Index.CALENDAR_ID)
            val end = cursor.getLong(Instances.Index.END)
            val endDay = cursor.getInt(Instances.Index.END_DAY)
            val eventColor = cursor.getInt(Instances.Index.EVENT_COLOR)
            val eventId = cursor.getLong(Instances.Index.EVENT_ID)
            val id = cursor.getLong(Instances.Index.ID)
            val startDay = cursor.getInt(Instances.Index.START_DAY)
            val title = cursor.getString(Instances.Index.TITLE)

            return Instance(
                allDay = allDay,
                begin = begin,
                calendarColor = calendarColor,
                calendarId = calendarId,
                end = end,
                endDay = endDay,
                eventColor = eventColor,
                eventId = eventId,
                id = id,
                startDay = startDay,
                title = title,
            )
        }
    }
}