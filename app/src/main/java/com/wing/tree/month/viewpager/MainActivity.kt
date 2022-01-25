package com.wing.tree.month.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.wing.tree.month.viewpager.databinding.ActivityMainBinding
import com.wing.tree.month.viewpager.presentation.adapter.MonthViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val monthViewPagerAdapter = MonthViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.viewPager2.apply {
            adapter = monthViewPagerAdapter
            offscreenPageLimit = 2
        }.setCurrentItem(MonthViewPagerAdapter.START_POSITION, false)
    }
}