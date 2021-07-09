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
        var readpayersList_subevents: ArrayList<Payers_subevent?>? = null
        var readnonpayersList_subevents: ArrayList<NonPayers_subevent?>? = null

    }
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
        if(receivesubeventid.hasExtra("newsubeventid"))
        {
            eid = receivesubeventid.getStringExtra("eventforsub_id")
            sid = receivesubeventid.getStringExtra("newsubeventid")
        }
        else if(receivesubeventid.hasExtra("Backfromrolestosubevent eid")) {
            eid = receivesubeventid.getStringExtra("Backfromrolestosubevent eid")
            sid = receivesubeventid.getStringExtra("Backfromrolestosubevent sid")
        }
        else if(receivesubeventid.hasExtra("backtosubevents - eid")) {
            eid = receivesubeventid.getStringExtra("backtosubevents - eid")
            sid = receivesubeventid.getStringExtra("backtosubevents - sid")
        } else if(receivesubeventid.hasExtra("readsubeventidview - eid")) {
            eid = receivesubeventid.getStringExtra("readsubeventidview - eid")
            sid = receivesubeventid.getStringExtra("readsubeventidview - sid")
        }

        ReadNameref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString()).child("SubEvent details")
        var readsubeventdetails = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    subeventobj = snapshot.getValue(subevents::class.java)
                    subevent_name.setText(subeventobj?.sname.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ReadNameref.addValueEventListener(readsubeventdetails)



        backbutton_subevents.setOnClickListener{
            val back_intent=Intent(this, EventActivity::class.java)
            back_intent.putExtra("eventidbackfromsubevent",eid)
            startActivity(back_intent)
            customType(this,"right-to-left")
            finish()
        }

        savesubeventname.setOnClickListener {             //name of the event
            subref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Events").child(eid.toString()).child("SubEvents").child(sid.toString()).child("SubEvent details")
            var subeventename = subevent_name.text.toString().trim()
            if (TextUtils.isEmpty(subeventename)) {
                subeventnamecounter++
                subeventename =  "Subevent ${subeventnamecounter}"
            }
            subevent = subevents(sid,subeventename)
            subref.setValue(subevent).addOnCompleteListener {
                Toast.makeText(baseContext,"Saved changes successfully!", Toast.LENGTH_LONG).show()
            }
        }

        addroles_subevents.setOnClickListener {
            val addrolessubevents_intent=Intent(this, rolesSubevent::class.java)
            addrolessubevents_intent.putExtra("Currenteventid",eid)
            addrolessubevents_intent.putExtra("Currentsubeventid",sid)
            startActivity(addrolessubevents_intent)
            customType(this,"left-to-right")
            finish()
        }

        close_subevent.setOnClickListener {
            //flag = 1
            val animationintent = Intent(this,CloseAnimation::class.java)
            startActivity(animationintent)
            finish()
            print("calling close subevent function")
            readpayerslistfun(object : FirebaseCallbackforpayers {
                override fun onCallbackp(payersdata: ArrayList<Payers_subevent?>?) {

                    Log.d("read payers data in interface function value", "$payersdata")
                    Log.d("readpayerslist subevents","$readpayersList_subevents")


                }
            })

            readnonpayerslistfun(object : FirebaseCallbackfornonpayers {
                override fun onCallbacknp(nonpayersdata: ArrayList<NonPayers_subevent?>?) {

                    Log.d("read nonpayers data in interface function value", "$nonpayersdata")
                    Log.d("readnonpayerslist subevents","$readnonpayersList_subevents")


                }
            })
            Log.d("payer data outside interface function", "$readpayersList_subevents")
            Log.d("nonpayer data outside interface fucntion","$readnonpayersList_subevents")
            //closesubevent()
        }
    }

    private interface FirebaseCallbackforpayers {
        fun onCallbackp(payercall: ArrayList<Payers_subevent?>?)
    }

    private interface FirebaseCallbackfornonpayers {
        fun onCallbacknp(payercall: ArrayList<NonPayers_subevent?>?)
    }

    private fun readpayerslistfun(myFirebaseCallbackforpayers: FirebaseCallbackforpayers){

        //reading payers data for close subevents
        GetPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString()).child("Roles SubEvents").child("Payers")
        val getpayersdata_subevents = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readpayersList_subevents?.clear()
                    for (counterobj in snapshot.children) {
                        val payerobj_subevents: Payers_subevent? =
                            counterobj.getValue(Payers_subevent::class.java)
                        readpayersList_subevents?.add(payerobj_subevents)

                        Log.d(
                            "reading payers data for close events in return function",
                            "name:${payerobj_subevents?.payerName_subevent.toString()}, amt:${payerobj_subevents?.payerAmt_subevent.toString()}"
                        )
                    }
                    myFirebaseCallbackforpayers.onCallbackp(readpayersList_subevents)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(PayersInputSubEvents)", Toast.LENGTH_SHORT).show()
            }
        }
        GetPayersref_subevents.addValueEventListener(getpayersdata_subevents)

    }



//
//
//    private fun storepayerslist(obj : Payers_subevent?) {
//        //if(flag == 1) {
//            readpayersList_subevents?.add(obj)
//            Log.d("store payers list function", "${readpayersList_subevents}")
//        //}
//    }
//
//
//    private fun storenonpayerslist(obj : NonPayers_subevent?) {
//        //if (flag == 1) {
//            readnonpayersList_subevents?.add(obj)
//            Log.d("store nonpayers list function", "${readnonpayersList_subevents}")
//        //}
//    }



    private fun readnonpayerslistfun(myFirebaseCallbackfornonpayers: FirebaseCallbackfornonpayers) {

        //reading non payers data for close subevents
        GetNonPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString()).child("Roles SubEvents")
            .child("Non Payers")
        val getnonpayersdata_subevents = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readnonpayersList_subevents?.clear()
                    for (counterobj in snapshot.children) {
                        val nonpayerobj_subevents: NonPayers_subevent? =
                            counterobj.getValue(NonPayers_subevent::class.java)
                        readnonpayersList_subevents?.add(nonpayerobj_subevents)
                        Log.d(
                            "reading non payers data for close events in return function",
                            nonpayerobj_subevents?.nonpayerName_subevent.toString()
                        )
                        readnonpayersList_subevents?.add(nonpayerobj_subevents)
                    }
                    myFirebaseCallbackfornonpayers.onCallbacknp(readnonpayersList_subevents)
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
        GetNonPayersref_subevents.addValueEventListener(getnonpayersdata_subevents)

    }



    private fun closesubevent() {


//        readpayerslistfun(object : MyCallback {
//            override fun onCallback(value: ArrayList<Payers_subevent?>?) {
//                Log.d("read payers data in interface function value", "$value")
//                Log.d("read payers data in readpayers list ","$readpayersList_subevents")
//            }
//        })

        //readpayersList_subevents = returnpayerslist()
        //readnonpayersList_subevents = returnnonpayerslist()


        Log.d("payer data after calling function", "$readpayersList_subevents")
        Log.d("nonpayer data after calling fucntion","$readnonpayersList_subevents")

        var amountsum : Float = 0.0F
        for(payer in readpayersList_subevents!!) {
            amountsum += (payer?.payerAmt_subevent).toString().toFloat()
        }

        //count of total member
        val totalmembers_subevent = (readpayersList_subevents!!.size) + (readnonpayersList_subevents!!.size)

        //split of each member
        val split_subevent = amountsum/totalmembers_subevent


        val splitforeach = ArrayList<SplitForEach_subevents?>() //class has members name and amt
        var npcount_sub = 0
        for(pcount_sub in 0..(readpayersList_subevents!!.size)-1) {
            splitforeach[pcount_sub]?.sub_amt = split_subevent - ((readpayersList_subevents!![pcount_sub]?.payerAmt_subevent).toString().toFloat())
            splitforeach[pcount_sub]?.sub_name  = readpayersList_subevents!![pcount_sub]?.payerName_subevent.toString()
            npcount_sub = pcount_sub
        }

        for(randomvariable in 0..(readnonpayersList_subevents!!.size)-1) {
            splitforeach[npcount_sub]?.sub_amt = split_subevent
            splitforeach[npcount_sub]?.sub_name = readnonpayersList_subevents!![npcount_sub]?.nonpayerName_subevent.toString()
            npcount_sub++
        }

        for(i in 0..(splitforeach.size)-1) {
            if(splitforeach[i]?.sub_amt!! < 0F)
            {
                println("To Receive: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else if(splitforeach[i]?.sub_amt!! > 0)
            {
                println("To Pay: Name: ${splitforeach[i]?.sub_name.toString()} Amount: ${abs(splitforeach[i]?.sub_amt!!.toFloat())}")
            }
            else
            {
                println("Majaaa maadi ${splitforeach[i]?.sub_name.toString()}")
            }
        }
    }
}