package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_payers_input.*
import kotlinx.android.synthetic.main.row_add_payer.*
import maes.tech.intentanim.CustomIntent.customType
import java.util.*
import kotlin.collections.ArrayList

//class PayersInput : AppCompatActivity() , View.OnClickListener {
class PayersInput : AppCompatActivity() {
    companion object{
        var i: Int = 1
        var payercount: Int = 1
        var readpayersList = ArrayList<Payers?>()
    }
    var layoutList: LinearLayout? = null
    private lateinit var Payersref: DatabaseReference
    private lateinit var GetPayersref: DatabaseReference
    var payersList = ArrayList<Payers>()
    var flag:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input)

        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()


        val intentcaller = intent
        var pid: String? = null     //==rid=eid
        var receiveintent = intent
        if(receiveintent.hasExtra("payerid")) {        //from roles page (rid) = eid
            pid  = receiveintent.getStringExtra("payerid")
        }
        else if(receiveintent.hasExtra("bothpayerid")){
            pid = receiveintent.getStringExtra("bothpayerid")
        }
        else if(receiveintent.hasExtra("backbothpayerid")){
            pid = receiveintent.getStringExtra("backbothpayerid")
        }


        GetPayersref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("Events").child(pid.toString())
            .child("Roles").child("Payers")
        var getpayersdata = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if(flag==0) {
                        for (counterobj in snapshot.children) {
                            val payerobj: Payers? = counterobj.getValue(Payers::class.java)
                            readpayersView(payerobj)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Firebase Database Exceptions called - onCancelled(PayersInput)",Toast.LENGTH_SHORT).show()
            }
        }
        GetPayersref.addValueEventListener(getpayersdata)

        //read data from firebase above here
        button_addpayers.setOnClickListener {
            addView()
        }


        //from here, writing data onto firebase
        button_createrolesforpayers.setOnClickListener {
            flag=1
//            Payersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
//            Payersref.child(pid.toString()).child("Roles").child("Payers").removeValue()
            i=1
            val result = checkIfValidAndRead()
            if(result) {
                Payersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                Payersref.child(pid.toString()).child("Roles").child("Payers").removeValue()
                for (counterobj in 0..payersList.size - 1) {
                    Payersref.child(pid.toString()).child("Roles").child("Payers").child("Payer $i").setValue(payersList[counterobj])
                    i++
                }
            }

            if(result) {
                if(intentcaller.hasExtra("bothpayerid") ) {
                    val intentcallfrompayersinput = Intent(this, NonPayersInput::class.java)
                    intentcallfrompayersinput.putExtra("bothpayerid", pid)
                    startActivity(intentcallfrompayersinput)
                    customType(this, "left-to-right")
                    finish()
                }
                else if(intentcaller.hasExtra("backbothpayerid")){
                    val intentcallfrompayersinput = Intent(this, NonPayersInput::class.java)
                    intentcallfrompayersinput.putExtra("bothpayerid", pid)
                    startActivity(intentcallfrompayersinput)
                    customType(this, "left-to-right")
                    finish()
                }
                else {
                    val intent = Intent(this, ConfirmRoles::class.java)
                    intent.putExtra("confirmrolesid", pid)
                    startActivity(intent)
                    finish()
                }
            }
        }

        backbutton_payersinput.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            intent.putExtra("backtorolespid", pid)
            startActivity(intent)
            customType(this, "right-to-left")
            finish()
        }

    }


    private fun checkIfValidAndRead(): Boolean {
        payersList.clear()
        var result = true
        for (i in 0 until layoutList!!.childCount) {
            val payerView = layoutList!!.getChildAt(i)
            val editPayersName = payerView.findViewById<View>(R.id.edit_payers_name) as EditText
            val editPayersAmt = payerView.findViewById<View>(R.id.edit_payers_amt) as EditText
            val payer = Payers()
            if (editPayersName.text.toString() != "") {
                payer.payerName = editPayersName.text.toString()
            } else {
                payer.payerName = "Payer $payercount"
//                Toast.makeText(baseContext, payer.payerName, Toast.LENGTH_SHORT).show()
                payercount++
            }
            for(j in 0 until i)
            {
                if(compareValues(payer.payerName, payersList[j].payerName)==0)
                {
                    Toast.makeText(baseContext,"Invalid. Differentiate same names with unique extra character(s)",Toast.LENGTH_LONG).show()
                    Log.d("Payersinput","Same name")
                    return false
                }
            }
            if (editPayersAmt.text.toString() != "") {
                payer.payerAmt = editPayersAmt.text.toString()
            } else {
                result = false
                break
            }
            payersList.add(payer)         //only after confirm roles is pressed, all the valid roles are stored in payersList
        }
        if (payersList.size == 0) {
            result = false
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        } else if (!result) {
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        }
        return result
    }

    private fun addView() {
        val payerView: View = layoutInflater.inflate(R.layout.row_add_payer, null, false)
        val pnamewrite = payerView.findViewById<View>(R.id.edit_payers_name) as EditText
        val pamtwrite = payerView.findViewById<View>(R.id.edit_payers_amt) as EditText
        pnamewrite.hint = "Payer's Name"
        pamtwrite.hint = "Amt"
        val imageClose = payerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(payerView) }
        layoutList!!.addView(payerView) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }

    private fun readpayersView(sampleobject: Payers?) {
        val payerViewx: View = layoutInflater.inflate(R.layout.row_add_payer, null, false)
        val pnamewrite = payerViewx.findViewById<View>(R.id.edit_payers_name) as EditText
        val pamtwrite = payerViewx.findViewById<View>(R.id.edit_payers_amt) as EditText
        pnamewrite.setText(sampleobject?.payerName.toString())
        pamtwrite.setText(sampleobject?.payerAmt.toString())
        val imageClose = payerViewx.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removepayerView(payerViewx) }
        layoutList!!.addView(payerViewx) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removepayerView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }

}