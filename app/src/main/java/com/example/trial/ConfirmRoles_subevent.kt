package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class ConfirmRoles_subevent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmrolessubevents)

        var eid:String? = null
        var sid:String? = null

        var receiverolesidintent = intent
        eid = receiverolesidintent.getStringExtra("confirmrolesid - eid")
        sid = receiverolesidintent.getStringExtra("confirmrolesid - sid")

        Toast.makeText(baseContext,"Roles created successfully", Toast.LENGTH_LONG).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, SubeventActivity::class.java)
            intent.putExtra("backtosubevents - eid",eid)
            intent.putExtra("backtosubevents - sid",sid)
            startActivity(intent)
        }, 2000)



    }
}