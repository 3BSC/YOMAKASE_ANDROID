package com.example.yomakase.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yomakase.databinding.DlgReservationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.example.yomakase.util.ResevationMinMaxDecorator
import java.util.*

class ReservationDialog: BottomSheetDialogFragment() {
    private lateinit var binding: DlgReservationBinding

    fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
    fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH)
    fun getCurrentDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DlgReservationBinding.inflate(inflater, container, false)

        val endTimeCalender = Calendar.getInstance()
        val startDay = CalendarDay.from(getCurrentYear(), getCurrentMonth(), getCurrentDay())
        val endDay = CalendarDay.from(getCurrentYear(), getCurrentMonth()+1, getCurrentDay())
        val minmaxDecorator = ResevationMinMaxDecorator(startDay, endDay)

        endTimeCalender.set(Calendar.MONTH, getCurrentMonth()+1)

        binding.calender.addDecorators(minmaxDecorator)
        binding.calender.selectedDate = CalendarDay.today()

        binding.calender.state().edit()
            .setMinimumDate(CalendarDay.from(getCurrentYear(), getCurrentMonth(), 1))
            .setMaximumDate(
                CalendarDay.from(getCurrentYear(), getCurrentMonth() + 1, endTimeCalender.getActualMaximum(
                    Calendar.DAY_OF_MONTH)))
            .commit()

        return binding.root
    }



}