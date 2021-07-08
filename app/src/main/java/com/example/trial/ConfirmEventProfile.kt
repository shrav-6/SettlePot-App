package com.example.trial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ConfirmEventProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_event_profile)

        Toast.makeText(baseContext,"Event Profile Updated!!", Toast.LENGTH_LONG).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,EventActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}