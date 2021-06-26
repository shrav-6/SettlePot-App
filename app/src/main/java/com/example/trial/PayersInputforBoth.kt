package com.example.trial

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_payers_input.*
import kotlinx.android.synthetic.main.activity_payers_inputfor_both.*
import kotlinx.android.synthetic.main.activity_payerspage.*

class PayersInputforBoth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_inputfor_both)

        backbuttonforpayersinputboth.setOnClickListener {
            val intent = Intent(this,PayersPageforBoth::class.java)
            startActivity(intent)
            finish()
        }

        createrolesforpayersboth.setOnClickListener {
            val createrolesforpayersbothintent1 = Intent(this, NonPayersPageforBoth::class.java)
            Log.d("payersinputforboth","changing to nonpayerspageforboth")
            startActivity(createrolesforpayersbothintent1)
            finish()
        }
    }
}
