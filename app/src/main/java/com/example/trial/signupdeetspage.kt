package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signupdeetspage.*

class signupdeetspage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupdeetspage)

        //go to sign up activity
        Buttonsignupsettlepot.setOnClickListener {
            val settlepotintent = Intent(this, signup::class.java)
            // start your next activity
            startActivity(settlepotintent)
            finish()
        }

        //go to login page activity
        alreadyacustomertext.setOnClickListener{
            val loginintent = Intent(this, logindeetspage::class.java)
            startActivity(loginintent)
            finish()
        }

        //go to google sign in page
        Buttonsignupmail.setOnClickListener{
            val googleintent = Intent(this, googlesignin::class.java)
            startActivity(googleintent)
            finish()
        }

        //go to phone sign in page
        Buttonsignupphone.setOnClickListener{
            val phoneintent = Intent(this, phonesignin::class.java)
            startActivity(phoneintent)
            finish()
        }
    }
}