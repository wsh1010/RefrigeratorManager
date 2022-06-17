package com.ggami.refrigeratormanager

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.ggami.refrigeratormanager.databinding.ActivityMessageDialogBinding

class MessageDialog(context: AppCompatActivity) {
    private var mBinding: ActivityMessageDialogBinding? = null
    private val binding get() = mBinding!!
    private val dlg = Dialog(context)
    private val parentActivity = context

    private lateinit var onOkClickListener: OnOkClickListener

    fun show(content: String) {
        mBinding = ActivityMessageDialogBinding.inflate(parentActivity.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        dlg.setCancelable(false)
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogMessage.text= content

        dlg.show()
        binding.positiveBtn.setOnClickListener {
            onOkClickListener.onOkClick()
            dlg.dismiss()
        }
        binding.negativeBtn.setOnClickListener {
            dlg.dismiss()
        }
    }

    fun disableNegativeBtn() {
        binding.negativeBtn.visibility = View.GONE
    }

    interface OnOkClickListener {
        fun onOkClick()
    }

    fun setOnOkClickListener(listener: OnOkClickListener) {
        onOkClickListener = listener
    }

}