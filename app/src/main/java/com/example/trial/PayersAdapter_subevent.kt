package com.example.trial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class PayersAdapter_subevent(payersList_subevent: ArrayList<Payers_subevent>) :
    RecyclerView.Adapter<PayersAdapter_subevent.PayersView_subevent>() {
    var payersList_subevent = ArrayList<Payers_subevent>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayersView_subevent {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_payers_subevent, parent, false)
        return PayersView_subevent(view)
    }

    override fun onBindViewHolder(holder: PayersView_subevent, position: Int) {
        val payer_subevent = payersList_subevent[position]
        holder.textPayersName_subevent.text = payer_subevent.payerName_subevent
        holder.textPayersAmt_subevent.text = payer_subevent.payerAmt_subevent
    }

    override fun getItemCount(): Int {
        return payersList_subevent.size
    }

    inner class PayersView_subevent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textPayersName_subevent: TextView
        var textPayersAmt_subevent: TextView

        init {
            textPayersName_subevent = itemView.findViewById<View>(R.id.text_payers_name_subevent) as TextView
            textPayersAmt_subevent = itemView.findViewById<View>(R.id.text_payers_amt_subevent) as TextView
        }
    }

    init {
        this.payersList_subevent = payersList_subevent
    }
}