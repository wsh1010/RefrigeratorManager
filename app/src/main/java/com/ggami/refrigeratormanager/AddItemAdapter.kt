package com.ggami.refrigeratormanager

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class AddItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
    val name = v.findViewById<EditText>(R.id.itemName)
    val count = v.findViewById<Spinner>(R.id.count)
    val category = v.findViewById<Spinner>(R.id.category)
    val closeButton = v.findViewById<ImageButton>(R.id.deleteButton)
}

class AddItemAdapter : RecyclerView.Adapter<AddItemViewHolder>(){
    private var addItemList = mutableListOf<RefrigeratorData.ItemInfo>()
    private lateinit var activity : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.additem_recycler, parent, false)
        activity = parent.context
        return AddItemViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        if (addItemList[position].name == "") {
            holder.name.hint = "상품 이름을 입력해주세요."
        }else {
            holder.name.setText(addItemList[position].name)
            Toast.makeText(activity, "test $position, ${addItemList[position].name}", Toast.LENGTH_SHORT).show()
        }
        /*val value = addItemList[position]
        if (value.name == "") {
            holder.name.hint = "상품 이름을 입력해주세요."
        }else {
            holder.name.setText(value.name)
            Toast.makeText(activity, "test $position, ${addItemList[position].name}", Toast.LENGTH_SHORT).show()
        }
        var category = activity.resources.getStringArray(R.array.category)
        var categoryAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, category)
        holder.category.adapter = categoryAdapter
        holder.category.setSelection(0)
        holder.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinner_position: Int, id: Long) {
                addItemList[position].category = category[spinner_position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        val countSize = mutableListOf<Int>(1)
        for (i in 2..99) {
            countSize.add(i)
        }
        val counterAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, countSize)
        holder.count.adapter = counterAdapter
        holder.count.setSelection(0)
        holder.count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinner_position
            : Int, id: Long) {
                addItemList[position].count = countSize[spinner_position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //var counterAdapter =
        //holder.category
        holder.closeButton.setOnClickListener {
            addItemList.remove(value)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }*/
    }
    override fun getItemCount(): Int {
        return addItemList.size
    }

    fun addItem(item : RefrigeratorData.ItemInfo) {
        addItemList.add(item)

    }

    fun getItemlist(): List<RefrigeratorData.ItemInfo> {
        return addItemList
    }



    interface OnItemClickEventListener {
        fun onItemClick(view:View, position : Int)

    }

    private lateinit var mItemClickListener : OnItemClickEventListener

    fun setOnItemClickListener(listener : OnItemClickEventListener) {
        mItemClickListener = listener
    }
}