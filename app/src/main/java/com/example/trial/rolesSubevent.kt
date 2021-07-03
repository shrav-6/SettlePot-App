package com.example.trial


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_roles.*

class rolesSubevent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roles)

        payers.setOnClickListener {
            val payers_intent = Intent(this, PayersInputSubevents::class.java)
            startActivity(payers_intent)
            finish()
        }

        nonpayers.setOnClickListener {
            val nonpayers_intent = Intent(this, NonPayersInputSubevents::class.java)
            startActivity(nonpayers_intent)
            finish()
        }

        both.setOnClickListener {
            val intent1 = Intent(this, PayersInputSubevents::class.java)
            intent1.putExtra("callerfromboth", "callerfromboth")
            startActivity(intent1)
            finish()
        }

        backbuttonrolespage.setOnClickListener{
            val backbuttonrolespage_intent = Intent(this, SubEvents::class.java)
            startActivity(backbuttonrolespage_intent)
            finish()
        }

        }
    }