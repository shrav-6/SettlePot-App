package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_nonpayers_input.*
import java.util.*


class NonPayersInput : AppCompatActivity() {
    companion object{
        var i: Int = 1
        var nonpayercount: Int = 1
        var readnonpayersList = ArrayList<NonPayers?>()
    }

    var layoutList: LinearLayout? = null
    private lateinit var Nonpayersref: DatabaseReference
    private lateinit var GetNonPayersref: DatabaseReference
    var nonpayersList = ArrayList<NonPayers>()
//    var readnonpayersList = ArrayList<NonPayers?>()
    var x: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers_input)
        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()


        val intentcaller = intent
        var npid: String? = null
        var receiverintent = intent
        if(receiverintent.hasExtra("nonpayerid")) {        //from roles page (rid) = eid
            npid  = receiverintent.getStringExtra("nonpayerid")
        }
        else if(receiverintent.hasExtra("bothpayerid")){
            npid = receiverintent.getStringExtra("bothpayerid")
        }


        GetNonPayersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").child(npid.toString()).child("Roles").child("Non Payers")
        var getnonpayersdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                if(!snapshot.exists()){
//                    readnonpayersList.clear()
//                }
                if (snapshot.exists()) {
                    readnonpayersList.clear()
                    for (counterobj in snapshot.children) {
//                        Toast.makeText(baseContext,"Counterobj val: $counterobj",Toast.LENGTH_LONG).show()
                        val nonpayerobj: NonPayers? = counterobj.getValue(NonPayers::class.java)
                        readnonpayersList.add(nonpayerobj)
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Firebase Database Exceptions called - onCancelled(Non PayersInput)",Toast.LENGTH_SHORT).show()
            }
        }
        GetNonPayersref.addValueEventListener(getnonpayersdata)

        for(loopobj in readnonpayersList) {
            Log.d("Values in readnonpayersList are:", "${loopobj?.nonpayerName}")
            readnonpayersView()
        }

        button_addnonpayers.setOnClickListener {
            addView()
        }



        button_createrolesfornonpayers.setOnClickListener {
            Nonpayersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            Nonpayersref.child(npid.toString()).child("Roles").child("Non Payers").removeValue()
            i=1
            val result = checkIfValidAndRead()
            if(result) {
                Nonpayersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                for (counterobj in 0..nonpayersList.size - 1) {
                    Nonpayersref.child(npid.toString()).child("Roles").child("Non Payers").child("Non Payer $i").setValue(nonpayersList[counterobj])
                    i++
                }
            }
            //write to database nonpayer objects before this
            if(result) {
                val intent = Intent(this, ConfirmRoles::class.java)
                intent.putExtra("confirmrolesid",npid)
                startActivity(intent)
                finish()
            }
        }

        backbutton_nonpayersinput.setOnClickListener {

            if(intentcaller.hasExtra("bothpayerid")) {
                val intentcallfromnonpayersinput = Intent(this, PayersInput::class.java)
                intentcallfromnonpayersinput.putExtra("backbothpayerid", npid)
                startActivity(intentcallfromnonpayersinput)
                finish()
            } else {
                val intent = Intent(this, RolesPage::class.java)
                intent.putExtra("backtorolesnpid",npid)
                startActivity(intent)
                finish()
            }
        }

    }


    private fun checkIfValidAndRead(): Boolean {
        nonpayersList.clear()
        var result = true

        for (i in 0 until layoutList!!.childCount) {
            val nonpayerView = layoutList!!.getChildAt(i)
            val editNonPayersName = nonpayerView.findViewById<View>(R.id.edit_nonpayers_name) as EditText
            val nonpayer = NonPayers()
            if (editNonPayersName.text.toString() != "") {
                nonpayer.nonpayerName = editNonPayersName.text.toString()
            } else {
                nonpayer.nonpayerName = "NonPayer $nonpayercount"
                Toast.makeText(baseContext, nonpayer.nonpayerName, Toast.LENGTH_SHORT).show()
                nonpayercount++
            }
            nonpayersList.add(nonpayer)
        }
        return result
    }

    private fun addView() {
        val nonpayerView: View = layoutInflater.inflate(R.layout.row_add_nonpayer, null, false)
        val imageClose = nonpayerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(nonpayerView) }
        layoutList!!.addView(nonpayerView)
    }
    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }

    private fun readnonpayersView() {
        val nonpayerViewx: View = layoutInflater.inflate(R.layout.row_add_nonpayer, null, false)
        val npnamewrite = nonpayerViewx.findViewById<View>(R.id.edit_nonpayers_name) as EditText
        npnamewrite.setText(readnonpayersList[x]?.nonpayerName.toString())
        x++
        val imageClose = nonpayerViewx.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removenonpayerView(nonpayerViewx) }
        layoutList!!.addView(nonpayerViewx) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removenonpayerView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }
}












