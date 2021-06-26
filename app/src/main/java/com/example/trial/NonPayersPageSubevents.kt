package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nonpayerspagesubevents.*


class NonPayersPageSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayerspagesubevents)

        backbuttonnonpayers_subevents.setOnClickListener{
            val backtorolesintent = Intent(this, rolesSubevent::class.java)
            startActivity(backtorolesintent)
            finish()
        }
        Continuenonpayerspage_subevents.setOnClickListener {
            val confirmnonpayerrolesintent = Intent(this, NonPayersInputSubevents::class.java)
            startActivity(confirmnonpayerrolesintent)
            finish()
        }


    }
}