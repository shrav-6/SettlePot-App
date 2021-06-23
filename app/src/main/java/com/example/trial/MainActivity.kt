package com.example.trial //contains Kotlin source code files
//com.example.<projname> (androidTest) - put all tests that run on an android device
//com.example.<projname> (test) - unit tests that don't need an android device to run
//res folder - all resources for the app (images, layout files, strings, icons, styling)
    //drawable: Images
    //Layout: UI layout files
    //mipmap: Launcher icons for app
    //values: Resources like strings, colors, etc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {   //handler use is to schedule messages and runnables to be executed at some point in the future
            val mainIntent = Intent(this, signupdeetspage::class.java)
            startActivity(mainIntent)
            this.finish()
        }, 3000)


    }
}

