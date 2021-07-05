package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
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
import java.util.*
import kotlin.collections.ArrayList

//class PayersInput : AppCompatActivity() , View.OnClickListener {
class PayersInput : AppCompatActivity() {
    var layoutList: LinearLayout? = null
    private lateinit var Payersref: DatabaseReference
    private lateinit var GetPayersref: DatabaseReference
//    var amtList: MutableList<String?> = ArrayList() //if not used, delete
    var payersList = ArrayList<Payers>()
    var readpayersList = ArrayList<Payers?>()
//    var pname: String? = null
//    var pamt: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input)




        layoutList = findViewById(R.id.layout_list)


        var i:Int = 1
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




        GetPayersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").child(pid.toString()).child("Roles").child("Payers")
        var getpayersdata = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    readpayersList.clear()
                    for (counterobj in snapshot.children) {
//                        Toast.makeText(baseContext,"Counterobj val: $counterobj",Toast.LENGTH_LONG).show()
                        val payerobj: Payers? = counterobj.getValue(Payers::class.java)
                        readpayersList.add(payerobj)
                    }
                    for(i in 0..readpayersList.size-1){
//                          Toast.makeText(baseContext,"Payer ${i+1}: ${readpayersList[i]?.payerName} Amount: ${readpayersList[i]?.payerAmt}",Toast.LENGTH_SHORT).show()
                          readpayersList[i]?.let { addPayerView(it) }
                    }
                    //for loop to display
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
            val result = checkIfValidAndRead()
            if(result) {
                Payersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                for (counterobj in 0..payersList.size - 1) {
                    Payersref.child(pid.toString()).child("Roles").child("Payers").child("Payer $i").setValue(payersList[counterobj])
                    i++
                }
            }
//            Payersref.child(pid.toString()).child("Roles").child("Payers").setValue(payersList)
            //finish writing payer objects before this
            if(result) {
                if(intentcaller.hasExtra("bothpayerid") ) {
                    val intentcallfrompayersinput = Intent(this, NonPayersInput::class.java)
                    intentcallfrompayersinput.putExtra("bothpayerid", pid)
                    startActivity(intentcallfrompayersinput)
                    finish()
                }
                else if(intentcaller.hasExtra("backbothpayerid")){
                    val intentcallfrompayersinput = Intent(this, NonPayersInput::class.java)
                    intentcallfrompayersinput.putExtra("bothpayerid", pid)
                    startActivity(intentcallfrompayersinput)
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
            finish()
        }

    }


    private fun checkIfValidAndRead(): Boolean {
        payersList.clear()
        var result = true
        var payercount = 1
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
            if (editPayersAmt.text.toString() != "") {
                payer.payerAmt = editPayersAmt.text.toString()
            } else {

                result = false
                break
            }
            payersList.add(payer)
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
        val imageClose = payerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(payerView) }
        layoutList!!.addView(payerView) //addView is an inbuilt func - not to be confused w the addView() function we have created
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view) //removeView is an inbuilt func
    }



    private fun addPayerView(payersampleobj: Payers) {
        val payerViewx: View = layoutInflater.inflate(R.layout.row_add_payer, null, false)
        edit_payers_name.hint = payersampleobj.payerName
        edit_payers_amt.hint = payersampleobj.payerAmt
        val imageClose = payerViewx.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(payerViewx) }
        layoutList!!.addView(payerViewx)
    }

}