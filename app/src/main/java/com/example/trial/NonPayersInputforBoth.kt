package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.HttpAuthHandler
import kotlinx.android.synthetic.main.activity_non_payers_inputfor_both.*

class NonPayersInputforBoth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_payers_inputfor_both)

        backbuttonfornonpayersinputforboth.setOnClickListener {
            val backtononpayerspageforbothintent = Intent(this, NonPayersPageforBoth::class.java)
            startActivity(backtononpayerspageforbothintent)
            finish()
        }

        createnonpayerrolesforboth.setOnClickListener {
            val createrolesforbothintent = Intent(this, ConfirmRoles::class.java)
            startActivity(createrolesforbothintent)
            finish()
        }
    }
}

