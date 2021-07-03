package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payers_input_subevents.*

class PayersInputSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input_subevents)

        backbuttonforpayersinput_subevent.setOnClickListener {
            val backtopayerspageintent = Intent(this, rolesSubevent::class.java)
            startActivity(backtopayerspageintent)
            finish()
        }

        createrolesforpayers_subevents.setOnClickListener {
            val createpayerrolesintent = Intent(this, ConfirmRolesSubevents::class.java)
            startActivity(createpayerrolesintent)
            finish()
        }
    }
}