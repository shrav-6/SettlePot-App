package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_event_activity.*

class EventActivity : AppCompatActivity() {
    companion object{
        var eventnamecounter: Int = 0
    }
    lateinit var event: events
    private lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_activity)

        var eid: String? = null
        var receiveidintent = intent
        if(receiveidintent.hasExtra("neweventid")) {
            eid  = receiveidintent.getStringExtra("neweventid")
        }
        else if( receiveidintent.hasExtra("eventnotesid")){
            eid = receiveidintent.getStringExtra("eventnotesid")
        }

        eventpagebackbutton.setOnClickListener {
//            Toast.makeText(baseContext,"Unsaved changes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, homepageevents::class.java)
            startActivity(intent)
            finish()
        }

        editeventprofile.setOnClickListener {
            val intent = Intent(this, EditEventProfile::class.java)
            startActivity(intent)
            finish()
        }


        notesbutton.setOnClickListener {
//            Toast.makeText(baseContext,"Notes button pressed!",Toast.LENGTH_LONG).show()
            var notespageintent = Intent(applicationContext, NotesInput::class.java)
            notespageintent.putExtra("eventid",eid)
            startActivity(notespageintent)
        }

        viewrolesbutton.setOnClickListener {
            Toast.makeText(baseContext,"View roles button pressed!",Toast.LENGTH_LONG).show()
        }

        viewsubeventsbutton.setOnClickListener {
            Toast.makeText(baseContext,"View Subevents button pressed!",Toast.LENGTH_SHORT).show()
        }

        addrolesbutton.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            startActivity(intent)
            finish()
        }

        addsubeventsbutton.setOnClickListener {
            val intent = Intent(this, SubEvents::class.java)
            startActivity(intent)
            finish()
        }
        savechangesbutton.setOnClickListener {
            ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            var eventename = eventName.text.toString().trim()
            if (TextUtils.isEmpty(eventename)) {
                eventnamecounter++
                eventename =  "Event $eventnamecounter"
            }
            event = events(eid,eventename)
            ref.child(eid.toString()).child("Event Details").setValue(event).addOnCompleteListener {
                Toast.makeText(baseContext,"Saved changes successfully!",Toast.LENGTH_LONG).show()
            }

        }

    }
}