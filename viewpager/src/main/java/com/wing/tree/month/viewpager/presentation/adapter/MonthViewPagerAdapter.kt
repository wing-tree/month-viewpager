package com.wing.tree.month.viewpager.presentation.adapter

import android.icu.util.Calendar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wing.tree.month.viewpager.presentation.view.MonthViewFragment

class MonthViewPagerAdapter : FragmentStateAdapter {
    constructor(fragment: Fragment): super(fragment.childFragmentManager, fragment.lifecycle)
    constructor(fragmentActivity: FragmentActivity): super(fragmentActivity.supportFragmentManager, fragmentActivity.lifecycle)

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        val amount = position - START_POSITION
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, amount)
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        return MonthViewFragment.instance(year, month)
    }

    companion object {
        private const val ITEM_COUNT = Int.MAX_VALUE
        const val START_POSITION = ITEM_COUNT / 2
    }
}