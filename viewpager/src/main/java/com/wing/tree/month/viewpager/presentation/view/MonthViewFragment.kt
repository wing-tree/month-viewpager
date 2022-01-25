package com.wing.tree.month.viewpager.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wing.tree.month.viewpager.key.Key
import com.wing.tree.month.viewpager.databinding.FragmentMonthBinding
import com.wing.tree.month.viewpager.data.model.Month
import com.wing.tree.month.viewpager.presentation.viewmodel.MonthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthViewFragment : Fragment() {
    private val viewModel by viewModels<MonthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val viewBinding = FragmentMonthBinding.inflate(inflater, container, false)
        val year = arguments?.getInt(Key.YEAR) ?: 0
        val month = arguments?.getInt(Key.MONTH) ?: 0

        return viewBinding.root.apply {
            viewModel.instances(year, month).observe(viewLifecycleOwner) {
                setInstances(it)
            }

            setMonth(
                Month(year, month).apply {
                    setOnDayClickListener(object : MonthView.OnDayClickListener {
                        override fun onDayClick(dayView: DayView?) {

                        }
                    })
                }
            )
        }
    }

    companion object {
        fun instance(year: Int, month: Int): MonthViewFragment {
            val bundle = Bundle().apply {
                putInt(Key.YEAR, year)
                putInt(Key.MONTH, month)
            }

            return MonthViewFragment().apply {
                arguments = bundle
            }
        }
    }
}