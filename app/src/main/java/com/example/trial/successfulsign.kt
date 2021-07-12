package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class successfulsign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfulsign)
        MainActivity.flag=1
        Log.d("Inside user account",MainActivity.flag.toString())

        //successful sign in gif activity using delay
        Handler(Looper.getMainLooper()).postDelayed(Runnable {   //handler use is to schedule messages and runnables to be executed at some point in the future
            val mainIntent = Intent(this, homepageevents::class.java)
            startActivity(mainIntent)
            finish()
        }, 4000)

    }
}

