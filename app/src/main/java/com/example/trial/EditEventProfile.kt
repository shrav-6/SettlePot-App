package com.example.trial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_event_profile.*
import android.content.Intent

class EditEventProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event_profile)

        backbuttonforeventdp.setOnClickListener {
            val intent = Intent(this,EventActivity::class.java)
            startActivity(intent)
            finish()
        }

        uploadfromdevice.setOnClickListener {
            //perform operation to upload event dp
        }

        confirmbutton.setOnClickListener {
            val intent = Intent(this,ConfirmEventProfile::class.java)
            startActivity(intent)
            finish()

        }
    }
}