package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_ratio_splitmainpage.*
import maes.tech.intentanim.CustomIntent.customType

class RatioSplitmainpage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratio_splitmainpage)

        backtohomepagebutton.setOnClickListener {
            val backtohomepageintent = Intent(this, homepageevents::class.java)
            startActivity(backtohomepageintent)
            customType(this,"right-to-left")
            finish()
        }
        addnewbutton.setOnClickListener {
            val computenewRatioSplitintent = Intent(this, RatioSplitInput::class.java)
            startActivity(computenewRatioSplitintent)
            customType(this, "left-to-right")
            finish()
        }

    }
}