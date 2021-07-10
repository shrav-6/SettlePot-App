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
import maes.tech.intentanim.CustomIntent.customType

class EventActivity : AppCompatActivity() {
    companion object {
        var eventnamecounter: Int = 0
        var subeventscounter: Int = 0
    }

    var readpList: MutableList<Payers?>? = mutableListOf()
    var readnpList: MutableList<NonPayers?>? = mutableListOf()
    var readsubplist: MutableList<Payers_subevent?>? = mutableListOf()
    var readsubnplist: MutableList<NonPayers_subevent?>? = mutableListOf()
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
                .child(eid.toString()) //.child("Roles").child("Payers")
            var getpayersdata = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        x = 0
                        readpList?.clear()
                        pcount = snapshot.child("Roles").child("Payers").childrenCount
                        for (counterobj in snapshot.child("Roles").child("Payers").children) {
                            var payerobj: Payers? = counterobj.getValue(Payers::class.java)
                            readpList?.add(payerobj)
                        }
                        x = pcount
                        for (countnumberpobj in snapshot.child("SubEvents").children) {
                            pcount += countnumberpobj.child("Roles SubEvents")
                                .child("Payers").childrenCount
                        }

                        for (anothercounterobj in snapshot.child("SubEvents").children) {
                            for (innerotherobject in anothercounterobj.child("Roles SubEvents")
                                .child("Payers").children) {
                                var sub_pobj: Payers_subevent? =
                                    innerotherobject.getValue(Payers_subevent::class.java)
                                readplist(sub_pobj, pcount)
                            }
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
//    private fun readplist_events(samplepobjevent: Payers?){
//        readpList?.add(samplepobjevent)
//    }

    private fun readplist(samplepobject: Payers_subevent?, pc: Long) {

        readsubplist?.add(samplepobject)
        ++x
        Log.d("inside readplist", "Value of x after ++X is: $x")

        if (x == pc) {
            var npcount: Long = 0
            GetNonPayersref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()) //.child("Roles").child("Non Payers")
            val getnonpayersdata = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        y = 0
                        readnpList?.clear()
                        npcount = snapshot.child("Roles").child("Non Payers").childrenCount
                        for (counterobj in snapshot.child("Roles").child("Non Payers").children) {
                            val nonpayerobj: NonPayers? = counterobj.getValue(NonPayers::class.java)
                            readnpList?.add(nonpayerobj)
                        }
                        y = npcount
                        for (countnumberobj in snapshot.child("SubEvents").children) //returns keys as sIDs
                        {
                            npcount += countnumberobj.child("Roles SubEvents")
                                .child("Non Payers").childrenCount
                        }
                        for (anothercounterobj in snapshot.child("SubEvents").children) //sIDs
                        {
                            for (innerotherobject in anothercounterobj.child("Roles SubEvents")
                                .child("Non Payers").children) {
                                var sub_npobj: NonPayers_subevent? =
                                    innerotherobject.getValue(NonPayers_subevent::class.java)
                                readnplist(sub_npobj, npcount)
                            }
//                            var nonpayerobj_sub: NonPayers_subevent? = anothercounterobj.child("Roles SubEvents").child("Non Payers").getValue(NonPayers_subevent::class.java)
//                            readnplist(nonpayerobj_sub,npcount)
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

    private fun readnplist(samplenpobj: NonPayers_subevent?, npc: Long) {

        readsubnplist?.add(samplenpobj)
        ++y

//        Log.d("inside readnplist", "Value of y after ++y is: $y")

        if (y == npc) {
//
//            Log.d("Line 188, inside y==npc in readplist: PAYERSLIST", readpList.toString())
//            Log.d("Line 188, inside y==npc in readnplist", readnpList.toString())
//            Log.d("Subpayers", readsubplist.toString())
//            Log.d("Subnonpayers", readsubnplist.toString())
            closeevent()
        }

    }


    private fun closeevent() {

        var uncleanarray: MutableList<SplitForEach_events> = mutableListOf()
        for (p_count in 0 until (readpList!!.size)) {
            var samplepobj = SplitForEach_events(readpList!!.get(p_count)?.payerName.toString(), readpList!!.get(p_count)?.payerAmt.toString().toFloat())
            uncleanarray.add(samplepobj)
        }
        for (p_count in 0 until readsubplist!!.size) {
            var samplesubpobj = SplitForEach_events(readsubplist!!.get(p_count)?.payerName_subevent.toString(), readsubplist!!.get(p_count)?.payerAmt_subevent.toString().toFloat())
            uncleanarray.add(samplesubpobj)
        }

        for (randomvariable in 0 until (readnpList!!.size)) {
            var samplenpobj = SplitForEach_events(readnpList!!.get(randomvariable)?.nonpayerName.toString(), 0.0F)
            uncleanarray.add(samplenpobj)
        }
        for (randomsubvariable in 0 until readsubnplist!!.size) {
            var samplesubnpobj = SplitForEach_events(readsubnplist!!.get(randomsubvariable)?.nonpayerName_subevent.toString(), 0.0F)
            uncleanarray.add(samplesubnpobj)
        }


        var samenameitem: String?
        for (outerloopvar in 0 until uncleanarray.size)
        {
            samenameitem = uncleanarray[outerloopvar].e_name
            for (innerloopvar in (outerloopvar + 1) until uncleanarray.size)
            {
                if (compareValues(uncleanarray[innerloopvar].e_name.trim(), samenameitem.trim()) == 0)
                {
                    uncleanarray[outerloopvar].e_amt += uncleanarray[innerloopvar].e_amt
                    uncleanarray[innerloopvar].e_amt = 0F
                    uncleanarray[innerloopvar].e_name = ""
                }
            }
        }

        var cleanarray : MutableList<SplitForEach_events> = mutableListOf()
        for (i in uncleanarray)
        {
            if(i.e_amt == 0F && i.e_name=="")
            {
                continue
            }
            else
                cleanarray.add(i)
        }

        //cleaned array above


        var amountsum: Float = 0.0F

        for (amountobject in cleanarray) {
            amountsum += amountobject.e_amt.toString().toFloat()
        }


        Log.d("Total amount for event", amountsum.toString())

        var totalmembers = cleanarray!!.size

        Log.d("Total number of members for event", totalmembers.toString())

        var split = amountsum / totalmembers

        for(unsplitobject in cleanarray)
        {
            unsplitobject.e_amt = split - unsplitobject.e_amt
            splitforeachevents.add(unsplitobject)
        }


        var topay_events: MutableList<SplitForEach_events> = mutableListOf()
        var toreceive_events: MutableList<SplitForEach_events> = mutableListOf()

        Log.d("Splitforeach: ", "$splitforeachevents")

        for (i in 0 until (splitforeachevents.size)) {
            if (splitforeachevents.get(i)?.e_amt!! < 0F) {
                toreceive_events.add(splitforeachevents.get(i)!!)
                println("To Receive: Name: ${splitforeachevents[i]?.e_name.toString()} Amount: ${Math.abs(splitforeachevents[i]?.e_amt!!.toFloat())}")
            } else if (splitforeachevents.get(i)?.e_amt!! > 0F) {
                topay_events.add(splitforeachevents.get(i)!!)
                println("To Pay: Name: ${splitforeachevents[i]?.e_name.toString()} Amount: ${Math.abs(splitforeachevents[i]?.e_amt!!.toFloat())}")
            } else if (splitforeachevents.get(i)?.e_amt == 0F) {
                println("Majaaa maadi ${splitforeachevents[i]?.e_name.toString()}")
            }
        }
    }

}


















//
//    //to remove same name entries within events and subevents
//    for(outerloopvar in 0 until splitforeachevents!!.size)
//    {
//        var samenameitem = splitforeachevents.get(outerloopvar)!!.e_name
//        for(innerloopvar in outerloopvar until splitforeachevents!!.size)
//        {
//            if(compareValues(splitforeachevents.get(innerloopvar)!!.e_name,samenameitem)==0)
//            {
//                splitforeachevents.get(outerloopvar)!!.e_amt += splitforeachevents.get(innerloopvar)!!.e_amt
//                splitforeachevents.removeAt(innerloopvar)
//            }
//        }
//    }


//    for(outerloopvar in 0 until splitforeachevents!!.size)
//    {
//        var samenameitem = splitforeachevents.get(outerloopvar).e_name
//        for(innerloopvar in outerloopvar until splitforeachevents!!.size)
//        {
//            if(compareValues(splitforeachevents.get(innerloopvar).e_name,samenameitem)==0)
//            {
//                splitforeachevents.removeAt(innerloopvar)
//            }
//        }
//    }

    //code wrong from here
//        for (p_count in 0 until (readpList!!.size)) {
////            Log.d("line 225 inside for loop: ", "pcount $pcount")
//            var samplepobj = SplitForEach_events(
//                readpList!!.get(p_count)?.payerName.toString(),
//                split - (readpList!!.get(p_count)?.payerAmt.toString().toFloat())
//            )
//            splitforeachevents.add(samplepobj)
//        }
//        for (p_count in 0 until readsubplist!!.size) {
//            var samplesubpobj = SplitForEach_events(
//                readsubplist!!.get(p_count)?.payerName_subevent.toString(),
//                split - (readsubplist!!.get(p_count)?.payerAmt_subevent.toString().toFloat())
//            )
//            splitforeachevents.add(samplesubpobj)
//        }
//
//        for (randomvariable in 0 until (readnpList!!.size)) {
//            var samplenpobj = SplitForEach_events(
//                readnpList!!.get(randomvariable)?.nonpayerName.toString(),
//                split
//            )
//            splitforeachevents.add(samplenpobj)
//        }
//        for (randomsubvariable in 0 until readsubnplist!!.size) {
//            var samplesubnpobj = SplitForEach_events(
//                readsubnplist!!.get(randomsubvariable)?.nonpayerName_subevent.toString(),
//                split
//            )
//            splitforeachevents.add(samplesubnpobj)
//        }






