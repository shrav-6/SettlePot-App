package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        backtologindeetspage.setOnClickListener {
            val backtologindeetspageintent = Intent(this, logindeetspage::class.java)
            startActivity(backtologindeetspageintent)
            finish()
        }
    }
}