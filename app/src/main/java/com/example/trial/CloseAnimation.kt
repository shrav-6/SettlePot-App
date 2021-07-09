package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CloseAnimation: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closinganimation)

        val callerintent = intent

        Toast.makeText(baseContext,"Settling bills!!", Toast.LENGTH_LONG).show()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,CloseEventsView::class.java)

            intent.putExtra("topay","topay")
            intent.putExtra("toreceive","toreceive")
            if(callerintent.hasExtra("ename")) {
                intent.putExtra("ename", "ename")
            } else if(callerintent.hasExtra("sname")) {
                intent.putExtra("sname","sname")
            }

            startActivity(intent)
            finish()
        }, 1500)

    }
}