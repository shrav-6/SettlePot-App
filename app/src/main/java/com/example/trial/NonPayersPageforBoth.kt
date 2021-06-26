package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_non_payersfor_both.*
import kotlinx.android.synthetic.main.activity_nonpayerspage.*

class NonPayersPageforBoth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_payersfor_both)

       backbuttonfornonpayerspageboth.setOnClickListener {
           val backtopayersinputpageforbothintent = Intent(this, PayersInputforBoth::class.java)
           startActivity(backtopayersinputpageforbothintent)
           finish()
       }

        Continuefornonpayerspageboth.setOnClickListener {
            val continuetononpayersinputpageintent = Intent(this, NonPayersInputforBoth::class.java)
            startActivity(continuetononpayersinputpageintent)
            finish()
        }
    }
}