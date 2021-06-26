package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ConfirmRolesSubevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmrolessubevents)

        Toast.makeText(baseContext,"Roles created successfully", Toast.LENGTH_LONG).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, SubEvents::class.java)
            startActivity(intent)
        }, 2000)
    }
}