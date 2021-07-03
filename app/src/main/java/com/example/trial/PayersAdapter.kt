package com.example.trial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class PayersAdapter(payersList: ArrayList<Payers>) :
    RecyclerView.Adapter<PayersAdapter.PayersView>() {
    var payersList = ArrayList<Payers>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayersView {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_payers, parent, false)
        return PayersView(view)
    }

    override fun onBindViewHolder(holder: PayersView, position: Int) {
        val payer = payersList[position]
        holder.textPayersName.text = payer.payerName
        holder.textPayersAmt.text = payer.payerAmt
    }

    override fun getItemCount(): Int {
        return payersList.size
    }

    inner class PayersView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textPayersName: TextView
        var textPayersAmt: TextView

        init {
            textPayersName = itemView.findViewById<View>(R.id.text_payers_name) as TextView
            textPayersAmt = itemView.findViewById<View>(R.id.text_payers_amt) as TextView
        }
    }

    init {
        this.payersList = payersList
    }
}