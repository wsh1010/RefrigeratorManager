package com.ggami.refrigeratormanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ggami.refrigeratormanager.databinding.ActivityAddItemBinding
import com.ggami.refrigeratormanager.databinding.ActivityInputDialogBinding
import com.ggami.refrigeratormanager.databinding.ActivityMessageDialogBinding
import java.time.LocalDate
import java.time.Period

class InputDialog(context: AppCompatActivity, ) {
    private var mBinding: ActivityInputDialogBinding? = null
    private val binding get() = mBinding!!
    private val dlg = Dialog(context)
    private val parentActivity = context
    private lateinit var expiredDate: LocalDate

    private lateinit var onOkClickListener: OnOkClickListener

    fun show(title: String, item: RefrigeratorData.ItemInfo) {
        mBinding = ActivityInputDialogBinding.inflate(parentActivity.layoutInflater)
        dlg.setContentView(binding.root)
        dlg.setCancelable(false)
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.titleIcon.setImageResource(R.drawable.ic_baseline_edit_24)
        binding.title.text = title

        binding.itemName.text = item.name
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate = dateFormat.parse(LocalDate.now().toString()).time
        val endDate = dateFormat.parse(item.expiredDate.toString()).time
        binding.expiredDay.text = "${((endDate-startDate) / (24*60*60*1000))} 일"
        binding.expiredDate.text = item.expiredDate.toString()
        binding.itemNum.text = "${item.count}"
        binding.category.text = item.category.Name

        expiredDate = item.expiredDate

        dlg.show()
        binding.positiveBtn.setOnClickListener {
            onOkClickListener.onOkClick(expiredDate)
            dlg.dismiss()
        }
        binding.negativeBtn.setOnClickListener {
            dlg.dismiss()
        }

        binding.calendarBtn.setOnClickListener {
            val dlg = DatePickerDialog(parentActivity, { _, year, month, dayOfMonth ->
                expiredDate = LocalDate.of(year, month+1, dayOfMonth)
                val startDate = dateFormat.parse(LocalDate.now().toString()).time
                val endDate = dateFormat.parse(expiredDate.toString()).time

                binding.expiredDate.text = "$expiredDate"
                binding.expiredDay.text = "${(endDate-startDate) / (24*60*60*1000)} 일"}, expiredDate.year, expiredDate.monthValue -1, expiredDate.dayOfMonth)
            dlg.show()
        }
    }

    interface OnOkClickListener {
        fun onOkClick(expiredDate: LocalDate)
    }

    fun setOnOkClickListener(listener: OnOkClickListener) {
        onOkClickListener = listener
    }

}