package com.ggami.refrigeratormanager.addItem

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ggami.refrigeratormanager.InputDialog
import com.ggami.refrigeratormanager.R
import com.ggami.refrigeratormanager.RefrigeratorData
import java.time.LocalDate
import java.time.Period


class AddItemSelectAdapter(parentActivity: Context) : RecyclerView.Adapter<AddItemSelectAdapter.AddItemSelectViewHolder>(){
    private var addItemList = mutableListOf<RefrigeratorData.ItemInfo>()
    private var activity : AppCompatActivity = parentActivity as AppCompatActivity
    private var selectedItem = mutableListOf<Int>()
    private var itemDateUnit: Int = 0 //0:일 1: 주 2: 월 3: 년
    private var dateSize: Int = 1

    lateinit var mItemClickListener : OnItemClickEventListener

    inner class AddItemSelectViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.itemName)
        val dateSize = v.findViewById<EditText>(R.id.dateSize)
        val editButton = v.findViewById<ImageButton>(R.id.editButton)
        val line = v.findViewById<LinearLayout>(R.id.addItemLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemSelectViewHolder {
        /*val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.additem_recycler, parent, false)
        activity = parent.context as AppCompatActivity
        return AddItemViewHolder(cellForRow)*/
        return AddItemSelectViewHolder((LayoutInflater.from(activity).inflate(R.layout.additem_select,parent,false)))
    }

    override fun onBindViewHolder(holder: AddItemSelectViewHolder, position: Int) {
        if (addItemList[position].name == "") {
            holder.name.text =""
        }else {
            holder.name.text = addItemList[position].name
        }

        if (selectedItem.find {it == position} == null ) {
            holder.line.setBackgroundResource(R.drawable.additem_selector_false_background)

        } else {

            holder.line.setBackgroundResource(R.drawable.additem_selector_true_background)
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate = dateFormat.parse(LocalDate.now().toString()).time
        val endDate = dateFormat.parse(addItemList[position].expiredDate.toString()).time
        holder.dateSize.setText(((endDate-startDate) / (24*60*60*1000)).toString())

        holder.dateSize.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                dateSize = if(holder.dateSize.text.toString() == ""){
                    0
                } else {
                    holder.dateSize.text.toString().toInt()
                }
                addItemList[position].expiredDate = LocalDate.now().plusDays(dateSize.toLong())
            }
            false
        }

        holder.dateSize.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                dateSize = if(holder.dateSize.text.toString() == ""){
                    0
                }else {
                    holder.dateSize.text.toString().toInt()
                }
                addItemList[position].expiredDate = LocalDate.now().plusDays(dateSize.toLong())
            }
        }

        holder.editButton.setOnClickListener {
            val dlg = InputDialog(activity)
            dlg.show("상세설정", addItemList[position])
            dlg.setOnOkClickListener(object: InputDialog.OnOkClickListener{
                override fun onOkClick(expiredDate: LocalDate) {
                    addItemList[position].expiredDate = expiredDate
                    notifyItemChanged(position)
                }

            })
        }

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(position)
            addItemList[position].selected = !addItemList[position].selected
            if (selectedItem.find {it == position} == null ) {
                selectedItem.add(position)
            } else {
                selectedItem.remove(position)
            }
            notifyItemChanged(position)
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

    fun selectInit() {
        selectedItem.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItemList(): MutableList<RefrigeratorData.ItemInfo> {
        var items = mutableListOf<RefrigeratorData.ItemInfo>()
        for (position in selectedItem) {
            items.add(addItemList[position])
        }
        return items
    }

    fun removeSelectedItems() {
        selectedItem.sort()
        selectedItem.reverse()
        for (i in 0 until selectedItem.size){
            addItemList.removeAt(selectedItem[i])
        }
        selectInit()
    }

    interface OnItemClickEventListener {
        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener : OnItemClickEventListener) {
        mItemClickListener = listener
    }
}