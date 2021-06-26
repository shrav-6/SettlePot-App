package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nonpayerspage.*
import kotlinx.android.synthetic.main.activity_payerspage.*

class PayersPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payerspage)

        backbuttonforpayerspage.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            startActivity(intent)
            finish()
        }

        ContinueforPayersPage.setOnClickListener {
            val intent = Intent(this, PayersInput::class.java)
            startActivity(intent)
            finish()
        }
    }
}