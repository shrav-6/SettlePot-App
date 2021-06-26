package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_roles_page.*

class RolesPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roles_page)

        backbuttonforrolespage.setOnClickListener {
            val intent = Intent(this,EventActivity::class.java)
            startActivity(intent)
            finish()
        }

        payersbutton.setOnClickListener {
            val intent = Intent(this, PayersPage::class.java)
            startActivity(intent)
            finish()
        }

        nonpayersbutton.setOnClickListener {
            val intent = Intent(this, NonPayersPage::class.java)
            startActivity(intent)
            finish()
        }

        bothbutton.setOnClickListener {
            val intent1 = Intent(this, PayersPageforBoth::class.java)
            startActivity(intent1)
            finish()
        }
    }
}