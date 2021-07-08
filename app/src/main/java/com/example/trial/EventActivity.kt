package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_event_activity.*

class EventActivity : AppCompatActivity() {
    companion object{
        var eventnamecounter: Int = 0
        var subeventscounter: Int = 0
    }
    lateinit var event: events
    private lateinit var subeventsreference: DatabaseReference
    private lateinit var ReadEventNameref: DatabaseReference
    private lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_activity)


        var eventobj: events?
        var eid: String? = null
        var ename: String? = null
        var receiveidintent = intent
        if(receiveidintent.hasExtra("neweventid")) {
            eid  = receiveidintent.getStringExtra("neweventid")
        }
        else if( receiveidintent.hasExtra("eventnotesid")){
            eid = receiveidintent.getStringExtra("eventnotesid")
        }
        else if(receiveidintent.hasExtra("backtoeventid")){
            eid = receiveidintent.getStringExtra("backtoeventid")
        }
        else if(receiveidintent.hasExtra("backfromrolesid")){
            eid = receiveidintent.getStringExtra("backfromrolesid")
        }
        else if(receiveidintent.hasExtra("eventidbackfromsubevent")){
            eid = receiveidintent.getStringExtra("eventidbackfromsubevent")
            //can receive sid also, commented in Subevent activity back button for now
        } else if(receiveidintent.hasExtra("readeventid")) {
            eid = receiveidintent.getStringExtra("readeventid")
        } else if(receiveidintent.hasExtra("eventid from subevents view")) {
            eid = receiveidintent.getStringExtra("eventid from subevents view")
        }

        ReadEventNameref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("Event Details")
        var readeventdetails = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    eventobj = snapshot.getValue(events::class.java)
                    eventName.setText(eventobj?.ename.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ReadEventNameref.addValueEventListener(readeventdetails)





        eventpagebackbutton.setOnClickListener {
//            Toast.makeText(baseContext,"Unsaved changes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, homepageevents::class.java)
            startActivity(intent)
            finish()
        }

        viewsubeventsbutton.setOnClickListener {

            val intentviewsubevents = Intent(this, SubEventsView::class.java)
            intentviewsubevents.putExtra("eventid_view",eid.toString())
            startActivity(intentviewsubevents)
            finish()
        }
//
//        editeventprofile.setOnClickListener {
//            val intent = Intent(this, EditEventProfile::class.java)
//            startActivity(intent)
//            finish()
//        }


        notesbutton.setOnClickListener {
//            Toast.makeText(baseContext,"Notes button pressed!",Toast.LENGTH_LONG).show()
            var notespageintent = Intent(applicationContext, NotesInput::class.java)
            notespageintent.putExtra("eventid",eid.toString())
            Log.d("Notes inside clicked event id: ", eid.toString()) //prints the correct notes id
            startActivity(notespageintent)
            finish()
        }


        addrolesbutton.setOnClickListener {
            val addrolesintent = Intent(this, RolesPage::class.java)
            addrolesintent.putExtra("rolesid",eid.toString())
            startActivity(addrolesintent)
            finish()
        }

        addsubeventsbutton.setOnClickListener {
            subeventscounter++
            subeventsreference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").child(eid.toString()).child("SubEvents")
            var newsid = subeventsreference.push().key.toString()
            Toast.makeText(baseContext,"No of subevents created: $subeventscounter",Toast.LENGTH_SHORT).show()
            val addsubeventsintent = Intent(this, SubeventActivity::class.java)
            addsubeventsintent.putExtra("eventforsub_id",eid)
            addsubeventsintent.putExtra("newsubeventid",newsid)
            PayersInputSubevents.payercount_subevents = 1
            PayersInputSubevents.readpayersList_subevents.clear()
            NonPayersInputSubevents.nonpayercount_subevents = 1
            NonPayersInputSubevents.readnonpayersList_subevents.clear()
            startActivity(addsubeventsintent)
            finish()
        }


        savechangesbutton.setOnClickListener {                //name of the event
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