package com.ggami.refrigeratormanager.addItem

import android.content.Context
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ggami.refrigeratormanager.R
import com.ggami.refrigeratormanager.RefrigeratorData
import java.time.LocalDate
import java.time.Period

class AddItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
    val name = v.findViewById<EditText>(R.id.itemName)
    val count = v.findViewById<Spinner>(R.id.count)
    val category = v.findViewById<Spinner>(R.id.category)
    val closeButton = v.findViewById<ImageButton>(R.id.deleteButton)
}

class AddItemAdapter(parentActivity: Context) : RecyclerView.Adapter<AddItemViewHolder>(){
    private var addItemList = mutableListOf<RefrigeratorData.ItemInfo>()
    private var activity : AppCompatActivity = parentActivity as AppCompatActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        /*val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.additem_recycler, parent, false)
        activity = parent.context as AppCompatActivity
        return AddItemViewHolder(cellForRow)*/
        return AddItemViewHolder((LayoutInflater.from(activity).inflate(R.layout.additem_recycler,parent,false)))
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        if (addItemList[position].name == "") {
            holder.name.hint = "상품 이름을 입력해주세요."
            holder.name.setText("")
        } else {
            holder.name.setText(addItemList[position].name)
        }

        holder.name.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addItemList[position].name = holder.name.text.toString()
            }
            false
        }

        holder.name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                addItemList[position].name = holder.name.text.toString()
            }
        }
        var category = activity.resources.getStringArray(R.array.category)
        var categoryAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, category)
        holder.category.adapter = categoryAdapter
        holder.category.setSelection(addItemList[position].category.ordinal)
        holder.category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinner_position: Int, id: Long) {
                addItemList[position].category = RefrigeratorData.getCategory(spinner_position)
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
        holder.count.setSelection(addItemList[position].count - 1)
        holder.count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinner_position
            : Int, id: Long) {
                addItemList[position].count = countSize[spinner_position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        holder.closeButton.setOnClickListener {
            addItemList.remove(addItemList[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }


    override fun getItemCount(): Int {
        return addItemList.size
    }

    fun addItem(item : RefrigeratorData.ItemInfo) {
        addItemList.add(item)

    }

    fun getItemList(): MutableList<RefrigeratorData.ItemInfo> {
        return addItemList
    }

    fun setItemList(list: MutableList<RefrigeratorData.ItemInfo>) {
        addItemList = list
    }

    interface OnItemClickEventListener {
        fun onItemClick(view:View, position : Int)

    }
    private lateinit var mItemClickListener : OnItemClickEventListener

    fun setOnItemClickListener(listener : OnItemClickEventListener) {
        mItemClickListener = listener
    }
}