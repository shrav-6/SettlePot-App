package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_homepageevents.*

class homepageevents : AppCompatActivity() {
    companion object{
        var eventscounter: Int = 0
    }
    private lateinit var reference: DatabaseReference
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
            eventscounter++
            reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            var neweid = reference.push().key.toString()
            Toast.makeText(baseContext, "No. of events created: $eventscounter", Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, EventActivity::class.java)
            intent.putExtra("neweventid", neweid)
            startActivity(intent)
        }


    }

}