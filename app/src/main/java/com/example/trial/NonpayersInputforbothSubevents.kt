package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nonpayers_inputforboth_subevents.*

class NonpayersInputforbothSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers_inputforboth_subevents)

        backbuttonfornonpayersinputforboth_subevent.setOnClickListener {
            val backtononpayerspageforbothintent = Intent(this, nonpayerspageforbothsubevents::class.java)
            startActivity(backtononpayerspageforbothintent)
            finish()
        }

        createnonpayerrolesforboth_subevent.setOnClickListener {
            val createrolesforbothintent = Intent(this, ConfirmRolesSubevents::class.java)
            startActivity(createrolesforbothintent)
            finish()
        }
    }
}