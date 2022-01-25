package com.wing.tree.month.viewpager.presentation.view

import android.content.Context
import android.graphics.*
import android.icu.util.Calendar
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.wing.tree.month.viewpager.constant.DAYS_PER_WEEK
import com.wing.tree.month.viewpager.data.model.Day
import com.wing.tree.month.viewpager.data.model.Month
import com.wing.tree.month.viewpager.presentation.ui.Alpha.decimal
import com.wing.tree.month.viewpager.presentation.ui.Color.Blue500
import com.wing.tree.month.viewpager.presentation.ui.Color.Red500
import com.wing.tree.month.viewpager.presentation.ui.TextSize
import com.wing.tree.month.viewpager.presentation.ui.px
import com.wing.tree.month.viewpager.util.double
import com.wing.tree.month.viewpager.util.float
import java.util.concurrent.atomic.AtomicBoolean

class DayView : View {
    private val isSelected = AtomicBoolean(false)
    private val coordinate = Coordinate(0.0F, 0.0F)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = Rect()
    private val rectF = RectF()

    var day: Day? = null

    constructor(context: Context, day: Day): super(context) {
        this.day = day
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        coordinate.clear()

        val day = this.day ?: return

        if (isSelected.get()) {
            paint.style = Paint.Style.STROKE
            paint.color = Color.MAGENTA
            paint.strokeWidth = 1.0F.px
            rect.set(0, 0, width, height)

            canvas.drawRect(rect, paint)
        }

        drawDayOfMonth(canvas, day)
        drawInstances(canvas, day)
    }

    private fun drawDayOfMonth(canvas: Canvas, day: Day) {
        coordinate.x += 2.0F.px
        coordinate.y += 2.0F.px

        val fontMetrics = Paint.FontMetrics()
        val text = "${day.dayOfMonth}"

        with(TextPaint(Paint.ANTI_ALIAS_FLAG)) {
            when(day.dayOfWeek) {
                Calendar.SUNDAY -> { color = Red500 }
                Calendar.SATURDAY -> { color = Blue500 }
            }

            alpha = when(day.type) {
                Month.Type.Previous -> 0.38F.decimal
                Month.Type.This -> 1.0F.decimal
                Month.Type.Next -> 0.38F.decimal
            }

            textSize = TextSize.DAY_OF_MONTH.px

            this.getFontMetrics(fontMetrics)

            if (day.julianDay == Day.today.julianDay) {
                this.color = Color.WHITE
                this.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                rectF.set(
                    coordinate.x - 2.0F.px,
                    coordinate.y,
                    coordinate.x + this.measureText(text) + 2.0F.px,
                    coordinate.y + fontMetrics.bottom - fontMetrics.top
                )

                with(Paint(Paint.ANTI_ALIAS_FLAG)) {
                    this.color = Color.GREEN

                    canvas.drawRoundRect(rectF, 4.0F.px, 4.0F.px, this)
                }
            }

            coordinate.y += fontMetrics.textHeight

            canvas.drawText(text, coordinate.x, coordinate.y, this)

            coordinate.y += 4.0F.px
        }
    }

    private fun drawInstances(canvas: Canvas, day: Day) {
        val fontMetrics = Paint.FontMetrics()
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            this.textSize = TextSize.INSTANCE.px
            this.style = Paint.Style.FILL
        }

        val widthF = parent.let {
            if (it is ViewGroup) {
                (it.width.double / DAYS_PER_WEEK.double).float
            } else {
                width.float
            }
        }

        paint.style = Paint.Style.FILL
        textPaint.getFontMetrics(fontMetrics)

        day.instances.forEach { instance ->
            instance?.let {
                val right = widthF * it.spanCount - 2.0F.px

                canvas.clipRect(0.0F, 0.0F, right, height.toFloat())

                val alpha = when(it.type) {
                    Month.Type.Previous -> 0.38F.decimal
                    Month.Type.This -> 1.0F.decimal
                    Month.Type.Next -> 0.38F.decimal
                }

                textPaint.alpha = alpha

                with(paint) {
                    this.color = it.calendarColor
                    this.alpha = alpha

                    val bottom = coordinate.y + fontMetrics.textHeight

                    if (it.isAllDay || it.spanCount > 1 || it.startDay < it.endDay) {
                        rectF.set(0.0F, coordinate.y, right, bottom)
                    } else {
                        rectF.set(0.0F, coordinate.y, 2.0F.px, bottom)
                    }

                    canvas.drawRect(rectF, this)
                }

                with(TextPaint(Paint.ANTI_ALIAS_FLAG)) {
                    this.alpha = alpha
                    this.textSize = TextSize.INSTANCE.px
                    this.style = Paint.Style.FILL

                    canvas.drawText(
                        it.title,
                        0.0F,
                        coordinate.y - textPaint.ascent(),
                        this
                    )
                }

                coordinate.y += fontMetrics.textHeight + 2.0F.px
            } ?: run {
                paint.color = Color.TRANSPARENT

                rectF.set(0.0F, coordinate.y, widthF,coordinate.y + fontMetrics.textHeight)
                canvas.drawRect(rectF, paint)

                coordinate.y += fontMetrics.textHeight + 2.0F.px
            }
        }
    }

    fun select() = run {
        isSelected.set(true)
        invalidate()
        this
    }

    fun unselect() {
        isSelected.set(false)
        invalidate()
    }

    private val Paint.FontMetrics.textHeight: Float get() = descent - ascent

    inner class Coordinate(var x: Float, var y: Float) {
        fun clear() {
            x = 0.0F
            y = 0.0F
        }
    }
}