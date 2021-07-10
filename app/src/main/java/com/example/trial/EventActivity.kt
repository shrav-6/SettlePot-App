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
import maes.tech.intentanim.CustomIntent
import maes.tech.intentanim.CustomIntent.customType

class EventActivity : AppCompatActivity() {
    companion object {
        var eventnamecounter: Int = 0
        var subeventscounter: Int = 0
    }

    var readpList: MutableList<Payers?>? = mutableListOf()
    var readnpList: MutableList<NonPayers?>? = mutableListOf()
    var splitforeachevents: MutableList<SplitForEach_events?> = mutableListOf()

    //class has members name and amt
    var x: Long = 0
    var y: Long = 0

    lateinit var event: events
    private lateinit var subeventsreference: DatabaseReference
    private lateinit var ReadEventNameref: DatabaseReference
    private lateinit var GetPayersref: DatabaseReference
    private lateinit var GetNonPayersref: DatabaseReference
    private lateinit var ref: DatabaseReference
    var eid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_activity)


        var eventobj: events?

        var ename: String? = null
        var receiveidintent = intent
        if (receiveidintent.hasExtra("neweventid")) {
            eid = receiveidintent.getStringExtra("neweventid")
        } else if (receiveidintent.hasExtra("eventnotesid")) {
            eid = receiveidintent.getStringExtra("eventnotesid")
        } else if (receiveidintent.hasExtra("backtoeventid")) {
            eid = receiveidintent.getStringExtra("backtoeventid")
        } else if (receiveidintent.hasExtra("backfromrolesid")) {
            eid = receiveidintent.getStringExtra("backfromrolesid")
        } else if (receiveidintent.hasExtra("eventidbackfromsubevent")) {
            eid = receiveidintent.getStringExtra("eventidbackfromsubevent")
            //can receive sid also, commented in Subevent activity back button for now
        } else if (receiveidintent.hasExtra("readeventid")) {
            eid = receiveidintent.getStringExtra("readeventid")
        } else if (receiveidintent.hasExtra("eventid from subevents view")) {
            eid = receiveidintent.getStringExtra("eventid from subevents view")
        }

        ReadEventNameref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("Event Details")
        var readeventdetails = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
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
            customType(this, "right-to-left")
            finish()
        }

        viewsubeventsbutton.setOnClickListener {

            val intentviewsubevents = Intent(this, SubEventsView::class.java)
            intentviewsubevents.putExtra("eventid_view", eid.toString())
            startActivity(intentviewsubevents)
            customType(this, "left-to-right")
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
            notespageintent.putExtra("eventid", eid.toString())
            Log.d("Notes inside clicked event id: ", eid.toString())//prints the correct notes id
            startActivity(notespageintent)
            customType(this, "left-to-right")
            finish()
        }


        addrolesbutton.setOnClickListener {
            val addrolesintent = Intent(this, RolesPage::class.java)
            addrolesintent.putExtra("rolesid", eid.toString())
            startActivity(addrolesintent)
            customType(this, "left-to-right")
            finish()
        }

        addsubeventsbutton.setOnClickListener {
            subeventscounter++
            subeventsreference = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("SubEvents")
            var newsid = subeventsreference.push().key.toString()
            Toast.makeText(
                baseContext,
                "No of subevents created: $subeventscounter",
                Toast.LENGTH_SHORT
            ).show()
            val addsubeventsintent = Intent(this, SubeventActivity::class.java)
            addsubeventsintent.putExtra("eventforsub_id", eid)
            addsubeventsintent.putExtra("newsubeventid", newsid)
            PayersInputSubevents.payercount_subevents = 1
            PayersInputSubevents.readpayersList_subevents.clear()
            NonPayersInputSubevents.nonpayercount_subevents = 1
            NonPayersInputSubevents.readnonpayersList_subevents.clear()
            SubeventActivity.subeventnamecounter = 0
            startActivity(addsubeventsintent)
            customType(this, "left-to-right")
            finish()
        }


        savechangesbutton.setOnClickListener {                //name of the event
            ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            var eventename = eventName.text.toString().trim()
            if (TextUtils.isEmpty(eventename)) {
                eventnamecounter++
                eventename = "Event $eventnamecounter"
            }
            event = events(eid, eventename)
            ref.child(eid.toString()).child("Event Details").setValue(event).addOnCompleteListener {
                Toast.makeText(baseContext, "Saved changes successfully!", Toast.LENGTH_LONG).show()
            }

        }


        closeventbutton.setOnClickListener {
            var pcount: Long = 0
            GetPayersref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("Roles").child("Payers")
            var getpayersdata = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        x = 0
                        readpList?.clear()
                        pcount = (snapshot.childrenCount)
                        for (counterobj in snapshot.children) {
                            var payerobj: Payers? = counterobj.getValue(Payers::class.java)
                            readplist(payerobj, pcount)
                            Log.d(
                                "Reading payerslist : Name and pcount is:  ",
                                "${payerobj?.payerName.toString()} and $pcount"
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        baseContext,
                        "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            GetPayersref.addValueEventListener(getpayersdata)
        }
    }

    private fun readplist(samplepobj: Payers?, pc: Long) {

        readpList?.add(samplepobj)
        Log.d(
            "Added sampleobj to readpayerslist",
            "${readpList?.get(x.toInt())?.payerName}"
        )

        x++
        Log.d("inside readplist", "Value of x after ++X is: $x")

        if (x == pc) {
            var npcount: Long = 0
            GetNonPayersref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("Roles").child("Non Payers")
            val getnonpayersdata = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        y=0
                        readnpList?.clear()
                        npcount = snapshot.childrenCount
                        for (counterobj in snapshot.children) {
                            val nonpayerobj: NonPayers? = counterobj.getValue(NonPayers::class.java)
                            readnplist(nonpayerobj, npcount)
                            Log.d(
                                "Reading nonpayerslist : Name and npcount is:  ",
                                "${nonpayerobj?.nonpayerName.toString()} and $npcount"
                            )

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        baseContext,
                        "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            GetNonPayersref.addValueEventListener(getnonpayersdata)

        }
    }

    private fun readnplist(samplenpobj: NonPayers?, npc: Long) {

        Log.d(
            "Reading nonpayerslist : Name is:  ",
            "${samplenpobj?.nonpayerName.toString()} "
        )
        readnpList?.add(samplenpobj)
        Log.d(
            "Added sampleobj to readnonpayerslist",
            "${readnpList?.get(y.toInt())?.nonpayerName}"
        )
        y++

        Log.d("inside readnplist", "Value of y after ++y is: $y")

        if (y == npc) {
//
            Log.d(
                "Line 188, inside y==npc in readplist: PAYERSLIST",
                "${readpList.toString()}"
            )
            Log.d("Line 188, inside y==npc in readnplist", "${readnpList.toString()}")
            closeevent()
        }

    }


    private fun closeevent() {

        var amountsum: Float = 0.0F

        for (payer in readpList!!) {
            amountsum += (payer?.payerAmt).toString().toFloat()
        }

        var totalmembers = (readpList!!.size) + (readnpList!!.size)


        var split = amountsum / totalmembers

        for (pcount in 0 until (readpList!!.size)) {
            Log.d("line 225 inside for loop: ", "pcount $pcount")
            var samplepobj = SplitForEach_events(
                readpList!!.get(pcount)?.payerName.toString(),
                split - (readpList!!.get(pcount)?.payerAmt.toString()
                    .toFloat())
            )
            splitforeachevents.add(samplepobj)

        }

        for (randomvariable in 0 until (readnpList!!.size)) {
            var samplenpobj = SplitForEach_events(
                readnpList!!.get(randomvariable)?.nonpayerName.toString(),
                split
            )
            splitforeachevents.add(samplenpobj)
        }

        var topay_events: MutableList<SplitForEach_events> = mutableListOf()
        var toreceive_events: MutableList<SplitForEach_events> = mutableListOf()
        Log.d("Splitforeach: ", "$splitforeachevents")
        for (i in 0..(splitforeachevents.size) - 1) {
            if (splitforeachevents.get(i)?.e_amt!! < 0F) {
                toreceive_events.add(splitforeachevents.get(i)!!)
                println(
                    "To Receive: Name: ${splitforeachevents[i]?.e_name.toString()} Amount: ${
                        Math.abs(
                            splitforeachevents[i]?.e_amt!!.toFloat()
                        )
                    }"
                )
            } else if (splitforeachevents.get(i)?.e_amt!! > 0F) {
                topay_events.add(splitforeachevents.get(i)!!)
                println(
                    "To Pay: Name: ${splitforeachevents[i]?.e_name.toString()} Amount: ${
                        Math.abs(
                            splitforeachevents[i]?.e_amt!!.toFloat()
                        )
                    }"
                )
            } else if (splitforeachevents.get(i)?.e_amt == 0F) {
                println("Majaaa maadi ${splitforeachevents[i]?.e_name.toString()}")
            }
        }
    }
}
