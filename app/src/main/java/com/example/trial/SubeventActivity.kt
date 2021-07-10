package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_subevents.*
import maes.tech.intentanim.CustomIntent.customType
import java.lang.Math.abs

class SubeventActivity : AppCompatActivity() {

    companion object{
        var subeventnamecounter: Int = 0
//        var readpayersList_subevents: ArrayList<Payers_subevent?>? = null
//        var readnonpayersList_subevents: ArrayList<NonPayers_subevent?>? = null

    }
    var readpList_subevents: MutableList<Payers_subevent?>? = mutableListOf()
    var readnpList_subevents: MutableList<NonPayers_subevent?>? = mutableListOf()
    var splitforeach : MutableList<SplitForEach_subevents?> = mutableListOf() //class has members name and amt
    var x:Long = 0
    var y:Long = 0
    var sid: String? = null
    var eid: String? = null
    lateinit var subevent: subevents
    private lateinit var subref: DatabaseReference
    private lateinit var ReadNameref : DatabaseReference
    private lateinit var GetPayersref_subevents: DatabaseReference
    private lateinit var GetNonPayersref_subevents: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subevents)

        var subeventobj: subevents?

        val receivesubeventid = intent
        if (receivesubeventid.hasExtra("newsubeventid")) {
            eid = receivesubeventid.getStringExtra("eventforsub_id")
            sid = receivesubeventid.getStringExtra("newsubeventid")
        } else if (receivesubeventid.hasExtra("Backfromrolestosubevent eid")) {
            eid = receivesubeventid.getStringExtra("Backfromrolestosubevent eid")
            sid = receivesubeventid.getStringExtra("Backfromrolestosubevent sid")
        } else if (receivesubeventid.hasExtra("backtosubevents - eid")) {
            eid = receivesubeventid.getStringExtra("backtosubevents - eid")
            sid = receivesubeventid.getStringExtra("backtosubevents - sid")
        } else if (receivesubeventid.hasExtra("readsubeventidview - eid")) {
            eid = receivesubeventid.getStringExtra("readsubeventidview - eid")
            sid = receivesubeventid.getStringExtra("readsubeventidview - sid")
        }

        ReadNameref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString())
            .child("SubEvent details")
        var readsubeventdetails = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    subeventobj = snapshot.getValue(subevents::class.java)
                    subevent_name.setText(subeventobj?.sname.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ReadNameref.addValueEventListener(readsubeventdetails)



        backbutton_subevents.setOnClickListener {
            val back_intent = Intent(this, EventActivity::class.java)
            back_intent.putExtra("eventidbackfromsubevent", eid)
            startActivity(back_intent)
            customType(this, "right-to-left")
            finish()
        }

        savesubeventname.setOnClickListener {             //name of the event
            subref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Events").child(eid.toString()).child("SubEvents").child(sid.toString())
                .child("SubEvent details")
            var subeventename = subevent_name.text.toString().trim()
            if (TextUtils.isEmpty(subeventename)) {
                subeventnamecounter++
                subeventename = "Subevent ${subeventnamecounter}"
            }
            subevent = subevents(sid, subeventename)
            subref.setValue(subevent).addOnCompleteListener {
                Toast.makeText(baseContext, "Saved changes successfully!", Toast.LENGTH_LONG).show()
            }
        }

        addroles_subevents.setOnClickListener {
            val addrolessubevents_intent = Intent(this, rolesSubevent::class.java)
            addrolessubevents_intent.putExtra("Currenteventid", eid)
            addrolessubevents_intent.putExtra("Currentsubeventid", sid)
            startActivity(addrolessubevents_intent)
            customType(this, "left-to-right")
            finish()
        }

        close_subevent.setOnClickListener {

            var pcount: Long = 0
            GetPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("SubEvents").child(sid.toString()).child("Roles SubEvents").child("Payers")
            val getpayersdata_subevents = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        x=0
                        readpList_subevents?.clear()
                        pcount = (snapshot.childrenCount)
                        for (counterobj in snapshot.children) {
                            val payerobj_subevents: Payers_subevent? = counterobj.getValue(Payers_subevent::class.java)
                            readplist(payerobj_subevents,pcount)
//                            Log.d("Reading payerslist : Name and pcount is:  ", "${payerobj_subevents?.payerName_subevent.toString()} and $pcount")
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)", Toast.LENGTH_SHORT).show()
                }
            }
            GetPayersref_subevents.addValueEventListener(getpayersdata_subevents)

        }

    }

    private fun readplist(samplepobj: Payers_subevent?, pc: Long) {


        readpList_subevents?.add(samplepobj)
        Log.d("Added sampleobj to readpayerslist","${readpList_subevents?.get(x.toInt())?.payerName_subevent}")
        x++
        Log.d("inside readplist","Value of x after ++X is: $x")

        if (x== pc) {
            var npcount: Long = 0
            GetNonPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("SubEvents").child(sid.toString())
                .child("Roles SubEvents")
                .child("Non Payers")
            val getnonpayersdata_subevents = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        y=0
                        readnpList_subevents?.clear()
                        npcount = snapshot.childrenCount
                        for (counterobj in snapshot.children) {
                            val nonpayerobj_subevents: NonPayers_subevent? =
                                counterobj.getValue(NonPayers_subevent::class.java)
                            readnplist(nonpayerobj_subevents, npcount)
//                            Log.d("Reading nonpayerslist : Name and npcount is:  ", "${nonpayerobj_subevents?.nonpayerName_subevent.toString()} and $npcount")

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)", Toast.LENGTH_SHORT).show()
                }
            }
            GetNonPayersref_subevents.addValueEventListener(getnonpayersdata_subevents)

        }
    }

    private fun readnplist(samplenpobj: NonPayers_subevent?, npc: Long) {

//        Log.d("Reading nonpayerslist : Name is:  ", "${samplenpobj?.nonpayerName_subevent.toString()} ")
        readnpList_subevents?.add(samplenpobj)
//        Log.d("Added sampleobj to readnonpayerslist","${readnpList_subevents?.get(y.toInt())?.nonpayerName_subevent}")
        y++

//        Log.d("inside readnplist","Value of y after ++y is: $y")

        if(y==npc)
        {
//
//            Log.d("Line 188, inside y==npc in readnplist: PAYERSLIST","${readpList_subevents.toString()}")
//            Log.d("Line 188, inside y==npc in readnplist","${readnpList_subevents.toString()}")
            closesubevent()
        }

    }



    private fun closesubevent() {

        var amountsum : Float = 0.0F

        for(payer in readpList_subevents!!) {
            amountsum += (payer?.payerAmt_subevent).toString().toFloat()
        }

        var totalmembers_subevent = (readpList_subevents!!.size) + (readnpList_subevents!!.size)


        var split_subevent = amountsum/totalmembers_subevent

        for(pcount_sub in 0 until (readpList_subevents!!.size)) {
//            Log.d("line 225 inside for loop: ", "pcount $pcount_sub")
            var samplepobj = SplitForEach_subevents(readpList_subevents!!.get(pcount_sub)?.payerName_subevent.toString() , split_subevent - (readpList_subevents!!.get(pcount_sub)?.payerAmt_subevent.toString().toFloat()))
            splitforeach.add(samplepobj)

        }

        for(randomvariable in 0 until (readnpList_subevents!!.size)) {
            var samplenpobj = SplitForEach_subevents(readnpList_subevents!!.get(randomvariable)?.nonpayerName_subevent.toString() , split_subevent )
            splitforeach.add(samplenpobj)
        }

        var topay_subevents : MutableList<SplitForEach_subevents> = mutableListOf()
        var toreceive_subevents : MutableList<SplitForEach_subevents> = mutableListOf()
//        Log.d("Splitforeach: ","$splitforeach")
        for(i in 0..(splitforeach.size)-1) {
            if(splitforeach.get(i)?.sub_amt!! < 0F)
            {
                toreceive_subevents.add(splitforeach.get(i)!!)
                println("To Receive: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else if(splitforeach.get(i)?.sub_amt!! > 0F)
            {
                topay_subevents.add(splitforeach.get(i)!!)
                println("To Pay: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else if(splitforeach.get(i)?.sub_amt == 0F)
            {
                println("Majaaa maadi ${splitforeach[i]?.sub_name.toString()}")
            }
        }
    }
}