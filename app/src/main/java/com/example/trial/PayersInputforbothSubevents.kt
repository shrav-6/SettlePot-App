package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_payers_inputforboth_subevents.*

class PayersInputforbothSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_inputforboth_subevents)

        backbuttonforpayersinputboth_subevents.setOnClickListener {
            val intent1 = Intent(this, payerspageforbothsubevents::class.java)
            startActivity(intent1)
            finish()
        }

        createrolesforpayersboth_subevents.setOnClickListener {
            val createrolesforpayersbothintent1 = Intent(this, nonpayerspageforbothsubevents::class.java)
            startActivity(createrolesforpayersbothintent1)
            finish()
        }
    }
}