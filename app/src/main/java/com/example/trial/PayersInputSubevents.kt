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
import kotlinx.android.synthetic.main.activity_payers_input.*
import kotlinx.android.synthetic.main.activity_payers_input_subevents.*
import kotlinx.android.synthetic.main.activity_roles_subevent.*
import java.util.ArrayList



class PayersInputSubevents : AppCompatActivity() {

    companion object {
        var i: Int = 1
        var payercount_subevents: Int = 1
        var readpayersList_subevents = ArrayList<Payers_subevent?>()
    }

    var layoutList: LinearLayout? = null
    private lateinit var Payersref_subevents: DatabaseReference
    private lateinit var GetPayersref_subevents: DatabaseReference
    var payersList_subevents = ArrayList<Payers_subevent>()
    var flag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input_subevents)

        layoutList = findViewById(R.id.layout_list)
        layoutList!!.clearAnimation()

        val intentcaller = intent
        var eid: String? = null
        var sid: String? = null
        var receiveintent = intent

        if (receiveintent.hasExtra("payerid_subevents - eid")) {        //from roles page (rid) = eid
            eid = receiveintent.getStringExtra("payerid_subevents - eid")
            sid = receiveintent.getStringExtra("payerid_subevents - sid")

        } else if (receiveintent.hasExtra("backbothpayerid_subevents - eid")) {
            eid = receiveintent.getStringExtra("backbothpayerid_subevents - eid")
            sid = receiveintent.getStringExtra("backbothpayerid_subevents - sid")

        } else if(receiveintent.hasExtra("callerfromboth_subevents - eid")) {
            eid = receiveintent.getStringExtra("callerfromboth_subevents - eid")
            sid = receiveintent.getStringExtra("callerfromboth_subevents - sid")
        }


        GetPayersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            .child(eid.toString()).child("SubEvents").child(sid.toString()).child("Roles SubEvents")
            .child("Payers")
        var getpayersdata_subevents = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if (flag == 0) {
                        for (counterobj in snapshot.children) {
                            val payerobj_subevents: Payers_subevent? =
                                counterobj.getValue(Payers_subevent::class.java)
                            readpayersView_subevent(payerobj_subevents)
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
        GetPayersref_subevents.addValueEventListener(getpayersdata_subevents)


        button_addpayers_subevent.setOnClickListener {
            addView()
        }

        button_createrolesforpayers_subevent.setOnClickListener {
            flag = 1
            Payersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                .child(eid.toString()).child("SubEvents")
            Payersref_subevents.child(sid.toString()).child("Roles SubEvents").child("Payers").removeValue()
            i = 1
            val result = checkIfValidAndRead()
            if (result) {
                Payersref_subevents = FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                    .child(eid.toString()).child("SubEvents")
                for (counterobj in 0..payersList_subevents.size - 1) {
                    Payersref_subevents.child(sid.toString()).child("Roles SubEvents").child("Payers")
                        .child("Payer ${i}").setValue(payersList_subevents[counterobj])
                    i++
                }
            }

            //finish writing payer objects before this
            if (result) {
                if (intentcaller.hasExtra("callerfromboth_subevents - eid")) {
                    val intentcallfrompayersinput =
                        Intent(this, NonPayersInputSubevents::class.java)
                    intentcallfrompayersinput.putExtra("callerfromboth_subevents - eid", eid)
                    intentcallfrompayersinput.putExtra("callerfromboth_subevents - sid", sid)
                    startActivity(intentcallfrompayersinput)
                    finish()

                } else if (intentcaller.hasExtra("backbothpayerid_subevents - eid")) {
                    val intentcallfrompayersinput =
                        Intent(this, NonPayersInputSubevents::class.java)
                    intentcallfrompayersinput.putExtra("bothpayerid_subevents - eid", eid)
                    intentcallfrompayersinput.putExtra("bothpayerid_subevents - sid", sid)
                    startActivity(intentcallfrompayersinput)
                    finish()

                } else {
                    val intent = Intent(this, ConfirmRoles_subevent::class.java)
                    intent.putExtra("confirmrolesid - eid", eid)
                    intent.putExtra("confirmrolesid - sid", sid)
                    startActivity(intent)
                    finish()
                }
            }
        }

        backbutton_payersinput_subevent.setOnClickListener {
            val intent = Intent(this, rolesSubevent::class.java)
            intent.putExtra("backtorolespid - eid", eid)
            intent.putExtra("backtorolespid - sid", sid)
            startActivity(intent)
            finish()
        }
    }

    private fun checkIfValidAndRead(): Boolean {
        payersList_subevents.clear()
        var result = true
        for (i in 0 until layoutList!!.childCount) {
            val payerView = layoutList!!.getChildAt(i)
            val editPayersName_subevents = payerView.findViewById<View>(R.id.edit_payers_name_subevent) as EditText
            val editPayersAmt_subevents = payerView.findViewById<View>(R.id.edit_payers_amt_subevent) as EditText
            val payer_subevent = Payers_subevent()
            if (editPayersName_subevents.text.toString() != "") {
                payer_subevent.payerName_subevent = editPayersName_subevents.text.toString()
            } else {
                payer_subevent.payerName_subevent = "Payer ${payercount_subevents}"
                payercount_subevents++
            }
            if (editPayersAmt_subevents.text.toString() != "") {
                payer_subevent.payerAmt_subevent = editPayersAmt_subevents.text.toString()
            } else {
                result = false
                break
            }
            payersList_subevents.add(payer_subevent)         //only after confirm roles is pressed, all the valid roles are stored in payersList
        }
        if (payersList_subevents.size == 0) {
            result = false
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        } else if (!result) {
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        }
        return result
    }

    private fun addView() {
        val payerView: View = layoutInflater.inflate(R.layout.row_add_payer_subevent, null, false)
        val pnamewrite = payerView.findViewById<View>(R.id.edit_payers_name_subevent) as EditText
        val pamtwrite = payerView.findViewById<View>(R.id.edit_payers_amt_subevent) as EditText
        pnamewrite.hint = "Payer's Name"
        pamtwrite.hint = "Amt"
        val imageClose = payerView.findViewById<View>(R.id.image_remove_subevent) as ImageView
        imageClose.setOnClickListener { removeView(payerView) }
        layoutList!!.addView(payerView) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }

    private fun readpayersView_subevent(sampleobj_subevents: Payers_subevent?) {
        val payerViewx: View = layoutInflater.inflate(R.layout.row_add_payer_subevent, null, false)
        val pnamewrite = payerViewx.findViewById<View>(R.id.edit_payers_name_subevent) as EditText
        val pamtwrite = payerViewx.findViewById<View>(R.id.edit_payers_amt_subevent) as EditText
        pnamewrite.setText(sampleobj_subevents?.payerName_subevent.toString())
        pamtwrite.setText(sampleobj_subevents?.payerAmt_subevent.toString())
        val imageClose = payerViewx.findViewById<View>(R.id.image_remove_subevent) as ImageView
        imageClose.setOnClickListener { removepayerView(payerViewx) }
        layoutList!!.addView(payerViewx) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removepayerView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }

}
