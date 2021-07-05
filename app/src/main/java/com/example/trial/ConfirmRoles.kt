package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ConfirmRoles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_roles)
        var confirm_id:String? = null
        var receiverolesidintent = intent
        confirm_id = receiverolesidintent.getStringExtra("confirmrolesid")
        Toast.makeText(baseContext,"Roles created successfully",Toast.LENGTH_LONG).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("backtoeventid",confirm_id)
            startActivity(intent)
            this.finish()
        }, 2000)
    }
}


