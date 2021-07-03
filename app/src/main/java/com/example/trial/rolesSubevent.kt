package com.example.trial


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_roles_subevent.*

class rolesSubevent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roles_subevent)

        payers_subevent.setOnClickListener {
            val payers_intent = Intent(this, PayersInputSubevents::class.java)
            startActivity(payers_intent)
            finish()
        }

        nonpayers_subevent.setOnClickListener {
            val nonpayers_intent = Intent(this, NonPayersInputSubevents::class.java)
            startActivity(nonpayers_intent)
            finish()
        }

        both_subevent.setOnClickListener {
            val intent1 = Intent(this, PayersInputSubevents::class.java)
            intent1.putExtra("callerfromboth", "callerfromboth")
            startActivity(intent1)
            finish()
        }

        backbuttonrolespage_subevent.setOnClickListener{
            val backbuttonrolespage_intent = Intent(this, SubEvents::class.java)
            startActivity(backbuttonrolespage_intent)
            finish()
        }

        }
    }