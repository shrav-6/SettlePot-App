package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.trial.ActivityNonPayers
import com.example.trial.ConfirmRoles
import com.example.trial.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nonpayers_input.*
import java.util.*


class NonPayersInput : AppCompatActivity(), View.OnClickListener {
    var layoutList: LinearLayout? = null
    private lateinit var Nonpayersref: DatabaseReference
    private var i:Int = 1
    var nonpayersList = ArrayList<NonPayers>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers_input)
        layoutList = findViewById(R.id.layout_list)



        val intentcaller = intent
        var npid: String? = null
        var receiverintent = intent
        if(receiverintent.hasExtra("nonpayerid")) {        //from roles page (rid) = eid
            npid  = receiverintent.getStringExtra("nonpayerid")
        }
        else if(receiverintent.hasExtra("bothpayerid")){
            npid = receiverintent.getStringExtra("bothpayerid")
        }




        button_addnonpayers.setOnClickListener {
            addView()
        }



        button_createrolesfornonpayers.setOnClickListener {
            val result = checkIfValidAndRead()
            Nonpayersref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            for(counterobj in 0..nonpayersList.size-1){
                Nonpayersref.child(npid.toString()).child("Roles").child("Non Payers").child("Non Payer $i").setValue(nonpayersList[counterobj])
                i++
            }
            //write to database nonpayer objects before this
            if(result) {
                val intent = Intent(this, ConfirmRoles::class.java)
                intent.putExtra("confirmrolesid",npid)
                startActivity(intent)
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
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_createrolesfornonpayers -> if (checkIfValidAndRead()) {
                val intent = Intent(this, ActivityNonPayers::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", nonpayersList)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }


    private fun checkIfValidAndRead(): Boolean {
        nonpayersList.clear()
        var result = true
        var nonpayercount = 1
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
        val editText = nonpayerView.findViewById<View>(R.id.edit_nonpayers_name) as EditText
        val imageClose = nonpayerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(nonpayerView) }
        layoutList!!.addView(nonpayerView)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }


}