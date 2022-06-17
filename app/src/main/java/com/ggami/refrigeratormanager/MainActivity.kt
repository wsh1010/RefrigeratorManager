package com.ggami.refrigeratormanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ggami.refrigeratormanager.addItem.AddItemActivity
import com.ggami.refrigeratormanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private val resultRefriCode = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        if (!RefrigeratorData.readRefriInfo(this)) {
            RefrigeratorData.init()
            RefrigeratorData.saveRefriInfo(this)
        }

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addItemButton.setOnClickListener {
            var intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.manageItemButton.setOnClickListener {
        }

        binding.settingButton.setOnClickListener {
            var intent = Intent(this, SelectRefriActivity::class.java)
            intent.putExtra("refriId", RefrigeratorData.refriInfo.id)
            startActivityForResult(intent,resultRefriCode)
        }

        /*binding.button.setOnClickListener {
            var intent = Intent(this, SelectRefri::class.java)
            intent.putExtra("refriId", RefrigeratorData.refriInfo.id)
            startActivityForResult(intent,resultRefriCode)
        }*/
    }

    override fun onResume() {
        super.onResume()
        setMyRefrigerator()
    }

    private fun setMyRefrigerator(){
        if(RefrigeratorData.refriInfo.id == 1 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator_1)
        }else if(RefrigeratorData.refriInfo.id == 2 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator_2)
        }
        if(RefrigeratorData.refriInfo.id == 3 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator_3)
        }
        if(RefrigeratorData.refriInfo.id == 4 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator_4)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data!!)
        when(requestCode) {
            resultRefriCode-> {
                if(resultCode == Activity.RESULT_OK){
                    RefrigeratorData.refriInfo.id = data!!.getIntExtra("refriId", -1)
                    RefrigeratorData.saveRefriInfo(this)
                }else{

                }

            }
        }
    }
}

