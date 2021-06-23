package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signupdeetspage.*

class signupdeetspage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupdeetspage)

        Buttonsignupmail.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            // start your next activity
            startActivity(intent)
        }

    }
}