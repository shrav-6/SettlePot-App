package com.example.trial

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sub_events_view.*
import maes.tech.intentanim.CustomIntent
import maes.tech.intentanim.CustomIntent.customType

class SubEventsView : AppCompatActivity() {

    private lateinit var GetSubEventsref: DatabaseReference
    private lateinit var GetDeleteSubEventsref: DatabaseReference
    lateinit var getsubeventsdata : ValueEventListener
    private lateinit var readdataforeventname: DatabaseReference
    var eventobj: events? = null
    var eid: String? = null
    var ename: String? = null
    var deletesubeventflag: Int = 0
    var layoutList_subevents: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_events_view)

        val callerintent = intent
        if(callerintent.hasExtra("eventid_view")) {
            eid  = callerintent.getStringExtra("eventid_view")
        }

        layoutList_subevents = findViewById(R.id.layout_list_subevents)
        layoutList_subevents!!.clearAnimation()

        readdataforeventname = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("Event Details")
        var readeventdetails = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    eventobj = snapshot.getValue(events::class.java)
                    eventname_subevents.setText(eventobj?.ename.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        readdataforeventname.addValueEventListener(readeventdetails)


        GetSubEventsref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("Events").child(eid.toString()).child("SubEvents")
        getsubeventsdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (counterobj in snapshot.children) {
                        val subeventsobj: subevents? = counterobj.child("SubEvent details").getValue(subevents::class.java)
                        if(deletesubeventflag == 0) {
                            readsubeventsView(subeventsobj)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubevents)", Toast.LENGTH_SHORT).show()
            }
        }
        GetSubEventsref.addValueEventListener(getsubeventsdata)


        backbutton_subeventsview.setOnClickListener {
            val backfromsubeventsview = Intent(this,EventActivity::class.java)
            backfromsubeventsview.putExtra("eventid from subevents view",eid)
            startActivity(backfromsubeventsview)
            customType(this, "right-to-left")
            finish()
        }

    }

    private fun removeView(view: View) {
        layoutList_subevents!!.removeView(view) //removeView is an inbuilt func
    }

    private fun readsubeventsView(sampleobject: subevents?) {
        val subeventView: View = layoutInflater.inflate(R.layout.row_add_subevents, null, false)
        val subeventwrite = subeventView.findViewById<View>(R.id.subevents_name) as Button
        subeventwrite.setText(sampleobject?.sname.toString())
        subeventwrite.setTag(sampleobject?.sid.toString())


        val deletesubevent = subeventView.findViewById<View>(R.id.delete_subevents) as ImageButton
        deletesubevent.setOnClickListener {

            var builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure?")
                .setTitle("Confirm Delete")
                .setPositiveButton(R.string.positive, DialogInterface.OnClickListener { dialog, id ->
                    // CONFIRM

                    deletesubeventflag = 1

                    GetDeleteSubEventsref = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child("Events").child(eid.toString()).child("SubEvents")
                    GetDeleteSubEventsref.child(sampleobject?.sid.toString()).removeValue()

                    removeView(subeventView)
                    dialog.cancel()
                })
                .setNegativeButton(R.string.negative, DialogInterface.OnClickListener { dialog, id ->
                    // CANCEL
                    dialog.cancel()
                })
            // Create the AlertDialog object and return it
            var alert = builder.create()
            alert.show()

        }



        Log.d("Inside readsubevents view - Name ", subeventwrite.text.toString())
        Log.d("Inside readsubevents view - Id ", subeventwrite.getTag().toString())
        layoutList_subevents!!.addView(subeventView)


        subeventwrite.setOnClickListener {
            val subeventstartintent = Intent(this,SubeventActivity::class.java)
            subeventstartintent.putExtra("readsubeventidview - eid", eid.toString())
            subeventstartintent.putExtra("readsubeventidview - sid", subeventwrite.getTag().toString())
            PayersInputSubevents.payercount_subevents = 1
            NonPayersInputSubevents.nonpayercount_subevents = 1
            startActivity(subeventstartintent)
            customType(this, "left-to-right")
            finish()
        }
    }
}