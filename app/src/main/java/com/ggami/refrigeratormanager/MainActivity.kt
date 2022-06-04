package com.ggami.refrigeratormanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.ggami.refrigeratormanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private val resultRefriCode = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        RefrigeratorData.readRefriInfo(this)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addItemButton.setOnClickListener {
            var intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        /*binding.button.setOnClickListener {
            var intent = Intent(this, SelectRefri::class.java)
            intent.putExtra("refriId", RefrigeratorData.refriInfo.id)
            startActivityForResult(intent,resultRefriCode)
        }*/

        setMyRefrigerator()
    }

    private fun setMyRefrigerator(){
        if(RefrigeratorData.refriInfo.id == 1 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator1)
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
        }else if(RefrigeratorData.refriInfo.id == 2 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator1)
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
        }
        if(RefrigeratorData.refriInfo.id == 3 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator1)
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
        }
        if(RefrigeratorData.refriInfo.id == 4 ) {
            binding.myRefrigerator.setBackgroundResource(R.drawable.refrigerator1)
            Toast.makeText(this, "4", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data!!)
        when(requestCode) {
            resultRefriCode-> {
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "refri id : ${data!!.getIntExtra("refriId", -1)}", Toast.LENGTH_SHORT).show()
                    RefrigeratorData.refriInfo.id = data!!.getIntExtra("refriId", -1)
                    RefrigeratorData.saveRefriInfo(this)
                }else{

                }

            }
        }
    }
}

