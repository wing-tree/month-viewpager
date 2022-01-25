package com.wing.tree.month.viewpager.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children
import com.wing.tree.month.viewpager.constant.WEEKS_PER_MONTH
import com.wing.tree.month.viewpager.data.model.Instance
import com.wing.tree.month.viewpager.data.model.Month
import com.wing.tree.month.viewpager.util.notNull

class MonthView : LinearLayout {
    private val weeks = ArrayList<WeekView>()
    private val days = LinkedHashMap<Int, DayView>()

    private var month: Month? = null
    private var selectedDayView: DayView? = null

    private var onDayClickListener: OnDayClickListener? = null

    init {
        orientation = VERTICAL
        clipChildren = false
        clipToPadding = false
    }

    interface OnDayClickListener {
        fun onDayClick(dayView: DayView?)
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    fun setMonth(month: Month) {
        this.month = month

        init(month)
    }

    fun setInstances(instances: LinkedHashMap<Int, List<Instance>>) {
        month?.setInstances(instances)

        children.forEach { week ->
            if(week is WeekView) {
                week.children.forEach { day ->
                    day.invalidate()
                }
            }
        }
    }

    fun setOnDayClickListener(onDayClickListener: OnDayClickListener) {
        this.onDayClickListener = onDayClickListener
    }

    fun selectDay(dayView: DayView): DayView? {
        if (selectedDayView.notNull) {
            selectedDayView?.unselect()
        }

        val julianDay = dayView.day?.julianDay ?: -1

        selectedDayView = days[julianDay]

        return selectedDayView?.select()
    }

    private fun init(month: Month) {
        removeAllViews()

        repeat(WEEKS_PER_MONTH) { index ->
            val week = month.weeks[index] ?: return@repeat
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0F)

            WeekView(context, week).also {
                it.layoutParams = layoutParams

                addView(it)
                weeks.add(it)

                it.days.forEach { day ->
                    val julianDay = day.day?.julianDay ?: -1

                    days[julianDay] = day
                    day.setOnClickListener {
                        onDayClickListener?.onDayClick(selectDay(day))
                    }
                }
            }
        }
    }
}