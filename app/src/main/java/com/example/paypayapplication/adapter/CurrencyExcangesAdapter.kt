package com.example.paypayapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paypayapplication.R

class CurrencyExcangesAdapter(val currencyNames : ArrayList<String>,
         val currencyValues : ArrayList<Double>) : RecyclerView.Adapter<CurrencyExcangesAdapter.ViewHolder>() {







    inner class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        var fName = view.findViewById<View>(R.id.name) as TextView
        var sName = view.findViewById<View>(R.id.second_name) as TextView
        var tName = view.findViewById<View>(R.id.third_name) as TextView


        var fValue = view.findViewById<View>(R.id.value) as TextView
        var sValue = view.findViewById<View>(R.id.second_valuevalue) as TextView
        var tValue = view.findViewById<View>(R.id.third_value) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO("Not yet implemented")
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        var size : Int = currencyNames.size/3

        if(currencyNames.size%3 != 0)
            size++

        return size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //  TODO("Not yet implemented")
        var offSet : Int = position*3

        if(offSet < currencyNames.size) {
            holder.fName.setText(currencyNames.get(offSet + 0))
            holder.fValue.setText("" + currencyValues.get(offSet + 0))
        }

        if(offSet+1 < currencyNames.size) {
            holder.sName.setText(currencyNames.get(offSet + 1))
            holder.sValue.setText("" + currencyValues.get(offSet + 1))
        }

        if(offSet+2 < currencyNames.size) {
            holder.tName.setText(currencyNames.get(offSet + 2))
            holder.tValue.setText("" + currencyValues.get(offSet + 2))
        }

    }
}