package com.wing.tree.month.viewpager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.wing.tree.month.viewpager.constant.DAYS_PER_WEEK
import com.wing.tree.month.viewpager.data.model.Week

class WeekView : LinearLayout {
    private var week: Week? = null
    private var selectedDayView: DayView? = null

    val days = ArrayList<DayView>()

    init {
        clipChildren = false
        clipToPadding = false
    }

    constructor(context: Context, week: Week): super(context) {
        this.week = week

        init(week)
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    fun setWeek(week: Week) {
        this.week = week

        init(week)
    }

    private fun init(week: Week) {
        removeAllViews()

        repeat(DAYS_PER_WEEK) { index ->
            val day = week.days[index] ?: return@repeat
            val layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0F)

            addView(
                DayView(context, day).apply {
                    this.layoutParams = layoutParams

                    days.add(this)
                }
            )
        }
    }
}