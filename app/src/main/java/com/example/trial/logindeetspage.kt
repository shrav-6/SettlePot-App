package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logindeetspage.*

class logindeetspage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logindeetspage)

        notacustomertext.setOnClickListener{
            val signupintent = Intent(this,signupdeetspage::class.java)
            startActivity(signupintent)
            finish()
        }
        loginviasettlepotbutton.setOnClickListener{
            val loginviasettlepotaccountintent = Intent(this, login::class.java)
            startActivity(loginviasettlepotaccountintent)
            finish()
        }
        loginviagooglebutton.setOnClickListener{
            val googleintent = Intent(this, googlesignin::class.java)
            startActivity(googleintent)
            finish()
        }
        loginviaphonebutton.setOnClickListener{
            val phoneintent = Intent(this, phonesignin::class.java)
            startActivity(phoneintent)
            finish()
        }


    }
}