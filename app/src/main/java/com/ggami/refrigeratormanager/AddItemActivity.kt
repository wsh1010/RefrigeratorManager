package com.ggami.refrigeratormanager

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggami.refrigeratormanager.databinding.ActivityAddItemBinding
import java.time.LocalDate


class AddItemActivity : AppCompatActivity() {
    private var mBinding: ActivityAddItemBinding? = null


    private val binding get() = mBinding!!
    private lateinit var adapter : AddItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_item)
        mBinding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)

        //var layoutManager = LinearLayoutManager(this)
        adapter = AddItemAdapter()
        binding.addItemList.adapter = adapter
        binding.addItemList.layoutManager = LinearLayoutManager(this)

        binding.addItemBtn.setOnClickListener {
            /*var dialog = AddItemDialog()
            dialog.show(supportFragmentManager, "addItemDialog")*/
            var item = RefrigeratorData.ItemInfo()
            item.name = ""

            adapter.addItem(item)
            adapter.notifyItemInserted(adapter.itemCount)

        }
        binding.completBtn.setOnClickListener {
            Toast.makeText(this, "ok ${adapter.itemCount}" , Toast.LENGTH_SHORT).show()
            for (value in adapter.getItemlist()){
                Log.e("testMessage", "$value")
            }
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //menuInflater.inflate(R.menu.toolbar_okmenu, menu)

        //return true
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "click" , Toast.LENGTH_SHORT).show()
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.okbutton -> {
                Toast.makeText(this, "ok" , Toast.LENGTH_SHORT).show()
                //val intent = Intent()
                //intent.putExtra("refriId", refriId)
                //setResult(Activity.RESULT_OK, intent)
                //finish()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }
}