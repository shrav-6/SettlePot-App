package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payerspage_subevents.*

class PayerspageSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payerspage_subevents)

        Continuepayerspage_subevents.setOnClickListener {
            val submitpayers_intent = Intent(this, PayersInputSubevents::class.java)
            startActivity(submitpayers_intent)
            finish()
        }

        backbuttonforpayerspage_subevent.setOnClickListener{
            val backbutton_intent = Intent(this, rolesSubevent::class.java)
            startActivity(backbutton_intent)
            finish()
        }

    }
}