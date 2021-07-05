package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_roles_page.*

class RolesPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roles_page)
        var rid: String? = null
        var receiveidfromrolespageintent = intent
        if(receiveidfromrolespageintent.hasExtra("rolesid")) {             //from eventactivity
            rid  = receiveidfromrolespageintent.getStringExtra("rolesid")
        }
        else if(receiveidfromrolespageintent.hasExtra("backtorolesnpid")){
            rid = receiveidfromrolespageintent.getStringExtra("backtorolesnpid")
        }
        else if(receiveidfromrolespageintent.hasExtra("backtorolespid")){
            rid = receiveidfromrolespageintent.getStringExtra("backtorolespid")
        }


        backbuttonforrolespage.setOnClickListener {
            val intent = Intent(this,EventActivity::class.java)
            intent.putExtra("backfromrolesid",rid)
            startActivity(intent)
            finish()
        }

        payersbutton.setOnClickListener {
            val payersintentfromrolespage = Intent(this, PayersInput::class.java)
            payersintentfromrolespage.putExtra("payerid",rid)
            startActivity(payersintentfromrolespage)
            finish()
        }

        nonpayersbutton.setOnClickListener {
            val nonpayersintentfromrolespage = Intent(this, NonPayersInput::class.java)
            nonpayersintentfromrolespage.putExtra("nonpayerid",rid)
            startActivity(nonpayersintentfromrolespage)
            finish()
        }

        bothbutton.setOnClickListener {
            val intent1 = Intent(this, PayersInput::class.java)
            intent1.putExtra("bothpayerid", rid)
            startActivity(intent1)
            finish()
        }
    }
}