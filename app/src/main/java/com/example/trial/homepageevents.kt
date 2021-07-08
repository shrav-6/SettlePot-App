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
                        //Log.d("Values in readeventslist are:", "${readeventslist[i]?.eid} and ${readeventslist[i]?.ename}")
                        //i++
//                        Log.d("entered homepageevents", "eid: ${eventsobj?.eid}, ename:${eventsobj?.ename}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInput)", Toast.LENGTH_SHORT).show()
            }
        }
        GetEventsref.addValueEventListener(geteventsdata)


//        Log.d("Before entering loop to call views", readeventslist.toString())



//        for(i in 0..(eventsbutton.size)-1){
//            Log.d("Button name", eventsbutton[i].text.toString())
//            Log.d("Button tag", eventsbutton[i].getTag() as String)
//        }

//        for(i in 0..(eventsbutton?.size!!)-1) {

//            eventsbutton?.get(i)?.setOnClickListener {
//                val intent = Intent(this, EventActivity::class.java)
//                intent.putExtra("readeventid", "${eventsbutton!![i].tag}")
//                startActivity(intent)
//                NotesInput.read_temp_notes_list.clear()
//                NotesInput.notes = null
//                NonPayersInput.readnonpayersList.clear()
//                NonPayersInput.nonpayercount = 1
//                PayersInput.readpayersList.clear()
//                PayersInput.payercount = 1
//                EventActivity.subeventscounter = 0
//                SubeventActivity.subeventnamecounter = 0
//                finish()
//            }
//        }

//        enamewrite.setOnClickListener {
//            val intent = Intent(this,EventActivity::class.java)
//            intent.putExtra("neweventid","${enamewrite.tag}")
//            startActivity(intent)
//            finish()
//        }


        editprofileicon.setOnClickListener {
            val profileintent = Intent(this, profilepage::class.java)
            startActivity(profileintent)
            finish()
        }
        logoutbutton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Logged out successfully", Toast.LENGTH_SHORT).show()
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
//    private fun onsuccesscall(){
//        for (loopobj in readeventslist) {
//            Log.d("Values in readeventslist are:", "${loopobj?.eid} and ${loopobj?.ename}")
//            readeventsView()
//        }
//    }

    private fun readeventsView(sampleobject: events?) {
        val eventView: View = layoutInflater.inflate(R.layout.row_add_events, null, false)
        val eventwrite = eventView.findViewById<View>(R.id.events_name) as Button
//        Log.d("in view function, event name", readeventslist[x]?.ename.toString())
        eventwrite.setText(sampleobject?.ename.toString())
        eventwrite.setTag(sampleobject?.eid.toString())
//        eventwrite.id = x
//        eventsbutton.add(eventwrite)
        Log.d("Inside readevents view - Name ", eventwrite.text.toString())
        Log.d("Inside readevents view - Id ", eventwrite.getTag().toString())
//        x++
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
            finish()
        }

//    private fun readeventsView() {
//        val eventView: View = layoutInflater.inflate(R.layout.row_add_events, null, false)
//        val eventwrite = eventView.findViewById<View>(R.id.events_name) as Button
//        Log.d("in view function, event name", readeventslist[x]?.ename.toString())
//        eventwrite.setText(readeventslist[x]?.ename.toString())
//        eventwrite.setTag(readeventslist[x]?.eid.toString())
////        eventwrite.id = x
////        eventsbutton.add(eventwrite)
//        Log.d("Inside readevents view - Name ", eventwrite.text.toString())
//        Log.d("Inside readevents view - Id ", eventwrite.getTag().toString())
//        x++
//        layoutList!!.addView(eventView)
//
//
//        eventwrite.setOnClickListener {
//            val eventstartintent = Intent(this,EventActivity::class.java)
//            eventstartintent.putExtra("readeventid", eventwrite.getTag().toString())
//            NotesInput.read_temp_notes_list.clear()
//            NotesInput.notes = null
//            NonPayersInput.readnonpayersList.clear()
//            NonPayersInput.nonpayercount = 1
//            PayersInput.readpayersList.clear()
//            PayersInput.payercount = 1
//            EventActivity.subeventscounter = 0
//            SubeventActivity.subeventnamecounter = 0
//            startActivity(eventstartintent)
//            finish()
//        }

//        for(i in 0..(eventsbutton!!.size) - 1) {
//            eventsbutton?.get(i)?.setOnClickListener {
//                val intent = Intent(this, EventActivity::class.java)
//                intent.putExtra("readeventid", "${eventsbutton!![i].tag}")
//                startActivity(intent)
//                NotesInput.read_temp_notes_list.clear()
//                NotesInput.notes = null
//                NonPayersInput.readnonpayersList.clear()
//                NonPayersInput.nonpayercount = 1
//                PayersInput.readpayersList.clear()
//                PayersInput.payercount = 1
//                EventActivity.subeventscounter = 0
//                SubeventActivity.subeventnamecounter = 0
//                finish()
//            }
//        }


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
//    fun sample() {
//        Log.d("Sample: ","Entered Sample")
//    }

}