package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homepageevents.*
import maes.tech.intentanim.CustomIntent
import maes.tech.intentanim.CustomIntent.customType

class homepageevents : AppCompatActivity() {
    companion object {

        var eventscounter: Int = 0
        //var readeventslist = ArrayList<events?>()

    }

    //    private lateinit var eventsbutton : ArrayList<Button>
    private lateinit var reference: DatabaseReference
    var layoutList: LinearLayout? = null
    private lateinit var GetEventsref: DatabaseReference
    var x: Int = 0
    var i: Int = 0
    lateinit var geteventsdata : ValueEventListener
//    var enamewrite = Button(this);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepageevents)


        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()


        GetEventsref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Events")
        geteventsdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
//                    readeventslist.clear()

                    for (counterobj in snapshot.children) {
                        val eventsobj: events? = counterobj.child("Event Details").getValue(events::class.java)
//                        readeventslist.add(eventsobj)
                        readeventsView(eventsobj)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInput)", Toast.LENGTH_SHORT).show()
            }
        }
        GetEventsref.addValueEventListener(geteventsdata)




        editprofileicon.setOnClickListener {
            val profileintent = Intent(this, profilepage::class.java)
            startActivity(profileintent)
            customType(this, "fadein-to-fadeout")
            finish()
        }
        logoutbutton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val logoutIntent = Intent(this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
            customType(this, "fadein-to-fadeout")
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
            NotesInput.read_temp_notes_list.clear()
            NotesInput.notes = null
            NonPayersInput.readnonpayersList.clear()
            NonPayersInput.nonpayercount = 1
            PayersInput.readpayersList.clear()
            PayersInput.payercount = 1
            EventActivity.subeventscounter = 0
            SubeventActivity.subeventnamecounter = 0
            finish()
        }


    }

    private fun readeventsView(sampleobject: events?) {
        val eventView: View = layoutInflater.inflate(R.layout.row_add_events, null, false)
        val eventwrite = eventView.findViewById<View>(R.id.events_name) as Button
        eventwrite.setText(sampleobject?.ename.toString())
        eventwrite.setTag(sampleobject?.eid.toString())
        Log.d("Inside readevents view - Name ", eventwrite.text.toString())
        Log.d("Inside readevents view - Id ", eventwrite.getTag().toString())
        layoutList!!.addView(eventView)


        eventwrite.setOnClickListener {
            val eventstartintent = Intent(this,EventActivity::class.java)
            eventstartintent.putExtra("readeventid", eventwrite.getTag().toString())
            NotesInput.read_temp_notes_list.clear()
            NotesInput.notes = null
            NonPayersInput.readnonpayersList.clear()
            NonPayersInput.nonpayercount = 1
            PayersInput.readpayersList.clear()
            PayersInput.payercount = 1
            EventActivity.subeventscounter = 0
            SubeventActivity.subeventnamecounter = 0
            startActivity(eventstartintent)
            customType(this, "bottom-to-top")
            finish()
        }
    }
}