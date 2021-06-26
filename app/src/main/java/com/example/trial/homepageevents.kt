package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homepageevents.*

class homepageevents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepageevents)

        editprofileicon.setOnClickListener{
            val profileintent = Intent(this, profilepage::class.java)
            startActivity(profileintent)
            finish()
        }
        logoutbutton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext,"Logged out successfully",Toast.LENGTH_SHORT).show()
            val logoutIntent = Intent(this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
            finish()
        }
        addeventbutton.setOnClickListener {
            val addeventfromhomepageintent = Intent(this, EventActivity::class.java)
            startActivity(addeventfromhomepageintent)
            finish()
        }



    }

}