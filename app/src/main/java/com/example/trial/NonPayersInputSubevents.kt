package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_nonpayers_input_subevent.*
import java.util.ArrayList

class NonPayersInputSubevents : AppCompatActivity(){

    companion object{
        var i: Int = 1
        var nonpayercount_subevents: Int = 1
        var readnonpayersList_subevents = ArrayList<NonPayers_subevent?>()
    }

    var layoutList: LinearLayout? = null
    private lateinit var Nonpayersref_subevents: DatabaseReference
    private lateinit var GetNonPayersref_subevents: DatabaseReference
    var nonpayersList_subevents = ArrayList<NonPayers_subevent>()

    var x: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers_input_subevent)
        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()

        val intentcaller = intent
        var eid: String? = null
        var sid: String? = null
        var receiverintent = intent

        if(receiverintent.hasExtra("nonpayerid_subevent - eid")) {
            eid  = receiverintent.getStringExtra("nonpayerid_subevent - eid")
            sid  = receiverintent.getStringExtra("nonpayerid_subevent - sid")

        }
        else if(receiverintent.hasExtra("callerfromboth_subevents - eid")){
            eid = receiverintent.getStringExtra("callerfromboth_subevents - eid")
            sid  = receiverintent.getStringExtra("callerfromboth_subevents - sid")

        }


        GetNonPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users").
        child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").
        child(eid.toString()).child("SubEvents").
        child(sid.toString()).child("Roles SubEvents").child("Non Payers")
        var getnonpayersdata_subevents = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readnonpayersList_subevents.clear()
                    for (counterobj in snapshot.children) {
                        val nonpayerobj_subevent: NonPayers_subevent? = counterobj.getValue(NonPayers_subevent::class.java)
                        readnonpayersList_subevents.add(nonpayerobj_subevent)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Firebase Database Exceptions called - onCancelled(NonPayers Input SubEvents)",Toast.LENGTH_SHORT).show()
            }
        }
        GetNonPayersref_subevents.addValueEventListener(getnonpayersdata_subevents)

        for(loopobj in readnonpayersList_subevents) {
            Log.d("Values in readnonpayersList_subevents are:", "${loopobj?.nonpayerName_subevent}")
            readnonpayersView_subevent()
        }

        button_addnonpayers_subevent.setOnClickListener {
            addView_subevent()
        }

        button_createrolesfornonpayers_subevent.setOnClickListener {
            Nonpayersref_subevents = FirebaseDatabase.getInstance().getReference("Users").
            child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").
            child(eid.toString()).child("SubEvents")
            Nonpayersref_subevents.child(sid.toString()).child("Roles SubEvents").child("Non Payers").removeValue()
            i =1
            val result = checkIfValidAndRead()
            if(result) {
                Nonpayersref_subevents = FirebaseDatabase.getInstance().getReference("Users").
                child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").
                child(eid.toString()).child("SubEvents")
                for (counterobj in 0..nonpayersList_subevents.size - 1) {
                    Nonpayersref_subevents.child(sid.toString()).child("Roles SubEvents").child("Non Payers").
                    child("Non Payer ${NonPayersInput.i}").setValue(nonpayersList_subevents[counterobj])
                    i++
                }
            }
            //write to database nonpayer objects before this
            if(result) {
                val intent = Intent(this, ConfirmRoles_subevent::class.java)
                intent.putExtra("confirmrolesid_subevent - eid",eid)
                intent.putExtra("confirmrolesid_subevent - sid",sid)
                startActivity(intent)
                finish()
            }
        }

        backbutton_nonpayersinput_subevent.setOnClickListener {

            if(intentcaller.hasExtra("callerfromboth_subevents - eid")) {
                val intentcallfromnonpayersinput_subevents = Intent(this, PayersInputSubevents::class.java)
                intentcallfromnonpayersinput_subevents.putExtra("callerfromboth_subevents - eid", eid)
                intentcallfromnonpayersinput_subevents.putExtra("callerfromboth_subevents - sid", sid)
                startActivity(intentcallfromnonpayersinput_subevents)
                finish()

            } else {
                val intent = Intent(this, rolesSubevent::class.java)
                intent.putExtra("backtorolesnpid_subevent - eid",eid)
                intent.putExtra("backtorolesnpid_subevent - sid",sid)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun checkIfValidAndRead(): Boolean {
        nonpayersList_subevents.clear()
        var result = true

        for (i in 0 until layoutList!!.childCount) {
            val nonpayerView_subevent = layoutList!!.getChildAt(i)
            val editNonPayersName_subevent = nonpayerView_subevent.findViewById<View>(R.id.edit_nonpayers_name_subevent) as EditText
            val nonpayer_subevent = NonPayers_subevent()
            if (editNonPayersName_subevent.text.toString() != "") {
                nonpayer_subevent.nonpayerName_subevent = editNonPayersName_subevent.text.toString()
            } else {
                nonpayer_subevent.nonpayerName_subevent= "NonPayer $nonpayercount_subevents"
                Toast.makeText(baseContext, nonpayer_subevent.nonpayerName_subevent, Toast.LENGTH_SHORT).show()
                nonpayercount_subevents++
            }
            nonpayersList_subevents.add(nonpayer_subevent)
        }
        return result
    }

    private fun addView_subevent() {
        val nonpayerView_subevent: View = layoutInflater.inflate(R.layout.row_add_nonpayer_subevent, null, false)
        val imageClose_subevent = nonpayerView_subevent.findViewById<View>(R.id.image_remove_nonpayers_subevent) as ImageView
        imageClose_subevent.setOnClickListener { removeView(nonpayerView_subevent) }
        layoutList!!.addView(nonpayerView_subevent)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }

    private fun readnonpayersView_subevent() {
        val nonpayerViewx_subevent: View = layoutInflater.inflate(R.layout.row_add_nonpayer_subevent, null, false)
        val npnamewrite_subevent = nonpayerViewx_subevent.findViewById<View>(R.id.edit_nonpayers_name_subevent) as EditText
        npnamewrite_subevent.setText(readnonpayersList_subevents[x]?.nonpayerName_subevent.toString())
        x++
        val imageClose_subevent = nonpayerViewx_subevent.findViewById<View>(R.id.image_remove_nonpayers_subevent) as ImageView
        imageClose_subevent.setOnClickListener { removenonpayerView_subevent(nonpayerViewx_subevent) }
        layoutList!!.addView(nonpayerViewx_subevent) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removenonpayerView_subevent(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }

}