package com.example.trial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class NonPayersAdapter(nonpayersList: ArrayList<NonPayers>) :
    RecyclerView.Adapter<NonPayersAdapter.NonPayersView>() {
    var nonpayersList = ArrayList<NonPayers>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonPayersView {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_nonpayers, parent, false)
        return NonPayersView(view)
    }

    override fun onBindViewHolder(holder: NonPayersView, position: Int) {
        val nonpayer = nonpayersList[position]
        holder.textNonPayersName.text = nonpayer.nonpayerName

    }

    override fun getItemCount(): Int {
        return nonpayersList.size
    }

    inner class NonPayersView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNonPayersName: TextView


        init {
            textNonPayersName = itemView.findViewById<View>(R.id.text_nonpayers_name) as TextView

        }
    }

    init {
        this.nonpayersList = nonpayersList
    }
}