package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homepageevents.*

class homepageevents : AppCompatActivity() {
    companion object{

        var eventscounter: Int = 0
        var readeventslist = ArrayList<events?>()

    }
    private var eventsbutton : ArrayList<Button>? = null
    private lateinit var reference: DatabaseReference
    var layoutList: LinearLayout? = null
    private lateinit var GetEventsref: DatabaseReference
    var x:Int = 0
    var i:Int = 0
    //var enamewrite = Button(this);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepageevents)

        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()


        GetEventsref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("Events")
        var geteventsdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readeventslist.clear()
                    for (counterobj in snapshot.children) {
                        val eventsobj: events? = counterobj.child("Event Details").getValue(events::class.java)
                        readeventslist.add(eventsobj)
                        //Log.d("Values in readeventslist are:", "${readeventslist[i]?.eid} and ${readeventslist[i]?.ename}")
                        //i++
                        Log.d("eventsobj","eid: ${eventsobj?.eid}, ename:${eventsobj?.ename}")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Firebase Database Exceptions called - onCancelled(PayersInput)",Toast.LENGTH_SHORT).show()
            }
        }
        GetEventsref.addValueEventListener(geteventsdata)

        for(loopobj in readeventslist) {
            Log.d("Values in readeventslist are:", "${loopobj?.eid} and ${loopobj?.ename}")
            readeventsView()
        }
        
        for(i in 0..(eventsbutton?.size!!)-1) { //bruh what weird syntax was that
            eventsbutton?.get(i)?.setOnClickListener {
                val intent = Intent(this, EventActivity::class.java)
                intent.putExtra("readeventid", "${eventsbutton!![i].tag}")
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
//        enamewrite.setOnClickListener {
//            val intent = Intent(this,EventActivity::class.java)
//            intent.putExtra("neweventid","${enamewrite.tag}")
//            startActivity(intent)
//            finish()
//        }


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

    private fun readeventsView() {
        val eventView: View = layoutInflater.inflate(R.layout.row_add_events, null, false)
        val enamewrite = eventView.findViewById<View>(R.id.events_name) as Button
        Log.d("in vew function, event name", readeventslist[x]?.ename.toString())
        enamewrite.setText(readeventslist[x]?.ename.toString())
        enamewrite.setTag(readeventslist[x]?.eid.toString())
        eventsbutton?.add(enamewrite)
        x++
        layoutList!!.addView(eventView)

//        enamewrite.setOnClickListener {
//            val intent = Intent(this,EventActivity::class.java)
//            intent.putExtra("readeventid","${enamewrite.tag}")
//            startActivity(intent)
//            NotesInput.read_temp_notes_list.clear()
//            NotesInput.notes = null
//            NonPayersInput.readnonpayersList.clear()
//            NonPayersInput.nonpayercount = 1
//            PayersInput.readpayersList.clear()
//            PayersInput.payercount = 1
//            EventActivity.subeventscounter = 0
//            SubeventActivity.subeventnamecounter = 0
//            finish()
//        }
    }
}