package com.example.trial

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transaction_page.*

class CloseEventsView:AppCompatActivity() {

    lateinit var splitforeachguy: ArrayList<SplitForEach_subevents>
    lateinit var layoutList: LinearLayout
    lateinit var headingdata:String
    var status = 1
    lateinit var topay: ArrayList<SplitForEach_subevents>
    lateinit var toreceive: ArrayList<SplitForEach_subevents>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_page)

        //get topay and toreceive and ename/sname data from intents

        layoutList = findViewById(R.id.layout_list_transactionpage)
        layoutList!!.clearAnimation()

        //add ename or sname of the event to view
        enamesnameView()

        //add heading "To pay to settlepot account" to view
        headingView()

        //add to pay list to view
        for(member in topay) {
            membersView()
        }

        //add heading "To receive to settlepot account" to view
        status = 0
        headingView()

        //add to receive list to view
        for(member in toreceive) {
            membersView()
        }

    }

    private fun enamesnameView() {
        val enamesnameview: View = layoutInflater.inflate(R.layout.activity_transaction_page,null,false)
        val enamesname = enamesnameview.findViewById<View>(R.id.ename_sname) as TextView
        enamesname.setText("ename or sname received from intents")
        layoutList.addView(enamesnameview)
    }

    private fun headingView() {
        val headingview: View = layoutInflater.inflate(R.layout.row_add_heading_transactionpage,null,false)
        val headingname = headingview.findViewById<View>(R.id.transaction_heading) as EditText
        if (status == 1) {
            headingdata = "To Pay to Settlepot account"
        } else {
            headingdata = "To Receive from Settlepot account"
        }
        headingname.setText("To Pay to Settlepot account:")
        headingname.setTextColor(Color.GREEN)
        layoutList!!.addView(headingview)
    }

    private fun membersView() {
        val membersview: View = layoutInflater.inflate(R.layout.row_add_member_nameamt,null,false)
        val membersname = membersview.findViewById<View>(R.id.edit_membername) as EditText
        val membersamt = membersview.findViewById<View>(R.id.edit_memberamt) as EditText

        membersname.setText("Members name")
        membersamt.setText("Members amt")
        layoutList!!.addView(membersview)
    }
}