package com.example.trial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class NonPayersAdapter_subevent(nonpayersList_subevent: ArrayList<NonPayers_subevent>) :
    RecyclerView.Adapter<NonPayersAdapter_subevent.NonPayersView_subevent>() {
    var nonpayersList_subevent = ArrayList<NonPayers_subevent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonPayersView_subevent {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_nonpayers_subevent, parent, false)
        return NonPayersView_subevent(view)
    }

    override fun onBindViewHolder(holder: NonPayersView_subevent, position: Int) {
        val nonpayer_subevent = nonpayersList_subevent[position]
        holder.textNonPayersName_subevent.text = nonpayer_subevent.nonpayerName_subevent

    }

    override fun getItemCount(): Int {
        return nonpayersList_subevent.size
    }

    inner class NonPayersView_subevent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNonPayersName_subevent: TextView


        init {
            textNonPayersName_subevent = itemView.findViewById<View>(R.id.text_nonpayers_name_subevent) as TextView

        }
    }

    init {
        this.nonpayersList_subevent = nonpayersList_subevent
    }
}