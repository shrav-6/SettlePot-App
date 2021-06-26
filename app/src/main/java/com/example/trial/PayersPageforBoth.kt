package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_payers_pagefor_both.*

class PayersPageforBoth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_pagefor_both)

        backbuttonforpayerspageboth.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            startActivity(intent)
            finish()
        }

        ContinueforPayersPageBoth.setOnClickListener {
            val continueforpayerspageintent = Intent(this, PayersInputforBoth::class.java)
            startActivity(continueforpayerspageintent)
            finish()
        }
    }
}