package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signupdeetspage.*

class signupdeetspage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupdeetspage)

        Buttonsignupsettlepot.setOnClickListener {
            val settlepotintent = Intent(this, signup::class.java)
            // start your next activity
            startActivity(settlepotintent)
            finish()
        }

        alreadyacustomertext.setOnClickListener{
            val loginintent = Intent(this, logindeetspage::class.java)
            startActivity(loginintent)
            finish()
        }
        Buttonsignupmail.setOnClickListener{
            val googleintent = Intent(this, googlesignin::class.java)
            startActivity(googleintent)
            finish()
        }
        Buttonsignupphone.setOnClickListener{
            val phoneintent = Intent(this, phonesignin::class.java)
            startActivity(phoneintent)
            finish()
        }
    }
}