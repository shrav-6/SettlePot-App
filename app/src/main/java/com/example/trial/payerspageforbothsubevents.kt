package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payerspageforboth.*

class payerspageforbothsubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payerspageforboth)

        backbuttonpayersforboth_subevent.setOnClickListener {
            val backtorolessubintent = Intent(this, rolesSubevent::class.java)
            startActivity(backtorolessubintent)
            finish()
        }

        Continuepayersforboth_subevents.setOnClickListener {
            val continuetopayersinputintent = Intent(this, PayersInputforbothSubevents::class.java)
            startActivity(continuetopayersinputintent)
            finish()
        }

    }
}