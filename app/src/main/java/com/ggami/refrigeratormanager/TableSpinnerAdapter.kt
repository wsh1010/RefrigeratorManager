package com.ggami.refrigeratormanager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.annotation.NonNull
import java.util.*

class TableSpinnerAdapter : ArrayAdapter<Objects>, SpinnerAdapter {
    private var activity : Context
    private var resourceId : Int = 0
    private var valuelist : MutableList<Objects> = mutableListOf()

    constructor( context: Context, resource: Int, objects: MutableList<Objects>) : super(context, resource, objects){
        activity = context
        resourceId = resource
        valuelist = objects
    }

    override fun getCount(): Int {
        return valuelist.size
    }

    override fun getItem(position: Int): Objects? {
        return valuelist[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return super.getView(position, convertView, parent)
    }
}