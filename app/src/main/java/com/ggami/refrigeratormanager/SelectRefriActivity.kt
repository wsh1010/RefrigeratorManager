package com.ggami.refrigeratormanager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import com.ggami.refrigeratormanager.databinding.ActivitySelectRefriBinding

class SelectRefriActivity : AppCompatActivity() {
    private var mBinding: ActivitySelectRefriBinding? = null
    private val binding get() = mBinding!!

    private var refriId = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_select_refri)
        mBinding = ActivitySelectRefriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)

        binding.refri1.setOnClickListener {
            selectRefri1()
        }

        binding.refri2.setOnClickListener {
            selectRefri2()
        }

        binding.refri3.setOnClickListener {
            selectRefri3()
        }

        binding.refri4.setOnClickListener {
            selectRefri4()
        }
    }

    override fun onResume() {
        super.onResume()
        var id = intent.getIntExtra("refriId", -1)
        when(id) {
            -1, 2 -> {
                selectRefri2()
            }
            1 -> {
                selectRefri1()
            }
            3 -> {
                selectRefri3()
            }
            4 -> {
                selectRefri4()
            }
        }
    }

    fun selectRefri1() {
        binding.refri1.isChecked = true
        binding.refri2.isChecked = false
        binding.refri3.isChecked = false
        binding.refri4.isChecked = false
        refriId = 1

    }
    fun selectRefri2() {
        binding.refri1.isChecked = false
        binding.refri2.isChecked = true
        binding.refri3.isChecked = false
        binding.refri4.isChecked = false
        refriId = 2
    }
    fun selectRefri3() {
        binding.refri1.isChecked = false
        binding.refri2.isChecked = false
        binding.refri3.isChecked = true
        binding.refri4.isChecked = false
        refriId = 3
    }
    fun selectRefri4() {
        binding.refri1.isChecked = false
        binding.refri2.isChecked = false
        binding.refri3.isChecked = false
        binding.refri4.isChecked = true
        refriId = 4
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_okmenu, menu)
        return true
        //return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.okbutton -> {
                val intent = Intent()
                intent.putExtra("refriId", refriId)
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}