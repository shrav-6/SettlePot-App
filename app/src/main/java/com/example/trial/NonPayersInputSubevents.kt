package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_non_payers_input_subevents.*

class NonPayersInputSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_payers_input_subevents)

        backbuttonfornonpayersinput_subevent.setOnClickListener {
            val backtononpayerspageintent = Intent(this, NonPayersPageSubevents::class.java)
            startActivity(backtononpayerspageintent)
            finish()
        }

        createnonpayerroles_subevent.setOnClickListener {
            val confirmpagefromnonpayersinputintent = Intent(this, ConfirmRolesSubevents::class.java)
            startActivity(confirmpagefromnonpayersinputintent)
            finish()
        }
    }
}