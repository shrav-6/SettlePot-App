package com.example.trial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nonpayerspage.*
import android.content.Intent

class NonPayersPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayerspage)

        backbuttonfornonpayerspage.setOnClickListener {
            val backtorolespageintent = Intent(this, RolesPage::class.java)
            startActivity(backtorolespageintent)
            finish()
        }
        Continuefornonpayerspage.setOnClickListener {
            val continuetononpayersinputpageintent = Intent(this, NonPayersInput::class.java)
            startActivity(continuetononpayersinputpageintent)
            finish()
        }

    }
}
