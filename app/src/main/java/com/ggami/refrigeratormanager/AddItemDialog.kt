package com.ggami.refrigeratormanager

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.ggami.refrigeratormanager.databinding.ActivityAddItemDialogBinding


class AddItemDialog : DialogFragment() {
    private var mBinding : ActivityAddItemDialogBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ActivityAddItemDialogBinding.inflate(inflater, container, false)
        //dialog.setContentView(binding.root)
        //return super.onCreateView(inflater, container, savedInstanceState)

        binding.receiptBtn.setOnClickListener{
            //영수증
            dismiss()
        }

        binding.writeBtn.setOnClickListener {
            //직접
            dismiss()
        }
        binding.barcodeBtn.setOnClickListener {
            //바코드
            dismiss()
        }
        isCancelable = false

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}