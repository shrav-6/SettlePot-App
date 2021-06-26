package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payerspage.*
import kotlinx.android.synthetic.main.activity_payers_input.*

class PayersInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input)

        backbuttonforpayersinput.setOnClickListener {
            val intent = Intent(this,PayersPage::class.java)
            startActivity(intent)
            finish()
        }

        //val numOfPayers = NoOfPayers.text.toString() as Int

        //generate numOfPayers times buttons in payersinputpage

        createrolesforpayers.setOnClickListener {

            //store member name and associated amount paid in database

            val intent = Intent(this,ConfirmRoles::class.java)
            startActivity(intent)
            finish()

        }
    }
}