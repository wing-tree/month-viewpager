package com.wing.tree.month.viewpager.data.repository.instance

import android.Manifest.permission.READ_CALENDAR
import android.content.ContentUris
import android.content.Context
import android.icu.util.Calendar
import android.provider.CalendarContract
import androidx.annotation.RequiresPermission
import com.wing.tree.month.viewpager.constant.DAYS_PER_WEEK
import com.wing.tree.month.viewpager.constant.WEEKS_PER_MONTH
import com.wing.tree.month.viewpager.data.model.Instance
import com.wing.tree.month.viewpager.data.query.parameter.Instances
import com.wing.tree.month.viewpager.domain.repository.instance.InstancesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InstancesRepositoryImpl(context: Context) : InstancesRepository {
    private val contentResolver = context.applicationContext.contentResolver

    @RequiresPermission(READ_CALENDAR)
    override fun instances(year: Int, month: Int): Flow<LinkedHashMap<Int, List<Instance>>> {
        val linkedHashMap = linkedMapOf<Int, List<Instance>>()

        return flow {
            repeat(WEEKS_PER_MONTH) {
                linkedHashMap[it] = instances(year, month, it.inc())
            }

            emit(linkedHashMap)
        }.flowOn(Dispatchers.IO)
    }

    private fun instances(year: Int, month: Int, weekOfMonth: Int): List<Instance> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, 1)
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.WEEK_OF_MONTH, weekOfMonth)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val begin = calendar.timeInMillis
        val end = calendar.apply {
            add(Calendar.DATE, DAYS_PER_WEEK.dec())
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis

        return instances(begin, end)
    }

    private fun instances(begin: Long, end: Long): List<Instance> {
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()

        ContentUris.appendId(builder, begin)
        ContentUris.appendId(builder, end)

        val cursor = contentResolver.query(
            builder.build(),
            Instances.projections,
            Instances.selection,
            Instances.selectionArgs,
            Instances.sortOrder
        ) ?: return emptyList()

        val mutableList = mutableListOf<Instance>()

        while (cursor.moveToNext()) {
            val instance = Instance.from(cursor)

            // if (instance.begin < begin && instance.duration < 1 && instance.isAllDay) continue
            // (??? ?????? case) begin??? ?????? ??? ??????, ?????? ????????????, allDay ??? ??????.
            // begin ??? ?????? ???, ?????? ????????? ???????????? ???????????? ?????? ??? ??????.
            // ????????? ????????????, allDay ??? ?????? ????????? ????????? ?????? ??? ??? ??? ??????, ?????? ???.
            // ?????? ????????? allDay ????????? ????????? ??????.

            mutableList.add(instance)
        }

        return mutableList
    }
}