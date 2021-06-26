package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nonpayerspageforboth.*

class nonpayerspageforbothsubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayerspageforboth)

        backbuttonnonpayersforboth_subevent.setOnClickListener{
            val backbuttonnonpayersforboth_intent = Intent(this,payerspageforbothsubevents::class.java)
            startActivity(backbuttonnonpayersforboth_intent)
            finish()
        }

        Continuenonpayersforboth_subevents.setOnClickListener {
            val continue_intent = Intent(this, NonpayersInputforbothSubevents::class.java)
            startActivity(continue_intent)
            finish()
        }
    }
}