package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_non_payers_input.*
import kotlinx.android.synthetic.main.activity_nonpayerspage.*
import kotlinx.android.synthetic.main.activity_payers_input.*
import kotlinx.android.synthetic.main.activity_payerspage.*

class NonPayersInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_payers_input)

        backbuttonfornonpayersinput.setOnClickListener {
            val backtononpayerspageintent = Intent(this, NonPayersPage::class.java)
            startActivity(backtononpayerspageintent)
            finish()
        }

        createnonpayerroles.setOnClickListener {
            val confirmpagefromnonpayersinputintent = Intent(this, ConfirmRoles::class.java)
            startActivity(confirmpagefromnonpayersinputintent)
            finish()
        }

    }
}