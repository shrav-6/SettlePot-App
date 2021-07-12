package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {


    companion object{
        var flag: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null){
            flag = savedInstanceState.getInt("userstatusflag")
        }

        setContentView(R.layout.activity_main)

        //go to homepage activity after displaying "SettlePot" gif
        Handler(Looper.getMainLooper()).postDelayed(Runnable {   //handler use is to schedule messages and runnables to be executed at some point in the future
            if (flag == 1) {
                val gotohomepageintentt = Intent(this, homepageevents::class.java)
                startActivity(gotohomepageintentt)
                finish()
            } else if (flag == 0) {
                val mainIntent = Intent(this, signupdeetspage::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, 3000)

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        flag = savedInstanceState.getInt("userstatusflag")
    }
}

