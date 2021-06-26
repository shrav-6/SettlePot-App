package com.example.trial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_subevents.*

class SubEvents : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subevents)

        backbutton_subevents.setOnClickListener{
            val back_intent=Intent(this, EventActivity::class.java)
            startActivity(back_intent)
            finish()
        }

        viewroles_subevents.setOnClickListener {
            print("roles existing")
        }

        addroles_subevents.setOnClickListener {
            val addrolessubevents_intent=Intent(this, rolesSubevent::class.java)
            startActivity(addrolessubevents_intent)
            finish()
        }

        close_subevent.setOnClickListener {
            print("closing subevent and go to event page")
        }
    }
}