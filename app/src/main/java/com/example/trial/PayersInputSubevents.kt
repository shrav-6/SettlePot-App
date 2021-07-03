package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_payers_input_subevents.*
import java.util.ArrayList



class PayersInputSubevents : AppCompatActivity() , View.OnClickListener {
    var layoutList: LinearLayout? = null

    var amtList: MutableList<String?> = ArrayList()
    var payersList_subevent = ArrayList<Payers_subevent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input_subevents)
        layoutList = findViewById(R.id.layout_list)

        val intentcaller = intent

        button_addpayers_subevent.setOnClickListener {
            addView()
        }
        button_createrolesforpayers_subevent.setOnClickListener {
            val result = checkIfValidAndRead()

            if (result) {
                if (intentcaller.hasExtra("callerfromboth")) {
                    val intentcallfrompayersinputsubevents = Intent(this, NonPayersInputSubevents::class.java)
                    intentcallfrompayersinputsubevents.putExtra("callerfromboth", "callerfromboth")
                    startActivity(intentcallfrompayersinputsubevents)
                    finish()
                } else {
                    val intent = Intent(this, ConfirmRoles_subevent::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        backbutton_payersinput_subevent.setOnClickListener {
            val intent = Intent(this, rolesSubevent::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_createrolesforpayers -> if (checkIfValidAndRead()) {
                val intent = Intent(this, ActivityNonPayers::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", payersList_subevent)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    private fun checkIfValidAndRead(): Boolean {
        payersList_subevent.clear()
        var result = true
        var payercountsubevent = 1
        for (i in 0 until layoutList!!.childCount) {
            val payerView_subevent = layoutList!!.getChildAt(i)
            val editPayersName_subevent = payerView_subevent.findViewById<View>(R.id.edit_payers_name_subevent) as EditText
            val editPayersAmt_subevent = payerView_subevent.findViewById<View>(R.id.edit_payers_amt_subevent) as EditText
            val payer_subevent = Payers_subevent()
            if (editPayersName_subevent.text.toString() != "") {
                payer_subevent.payerName_subevent = editPayersName_subevent.text.toString()
            } else {
                payer_subevent.payerName_subevent = "Payer $payercountsubevent"
                Toast.makeText(baseContext, payer_subevent.payerName_subevent, Toast.LENGTH_SHORT).show()
                payercountsubevent++
            }
            if (editPayersAmt_subevent.text.toString() != "") {
                payer_subevent.payerAmt_subevent = editPayersName_subevent.text.toString()
            } else {
                result = false
                break
            }
            payersList_subevent.add(payer_subevent)
        }
        if (payersList_subevent.size == 0) {
            result = false
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        } else if (!result) {
            Toast.makeText(baseContext, "Enter Valid details!!", Toast.LENGTH_SHORT).show()
        }
        return result
    }

    private fun addView() {
        val payerView_subevent: View = layoutInflater.inflate(R.layout.row_add_payer_subevent, null, false)
        val editText_subevent = payerView_subevent.findViewById<View>(R.id.edit_payers_name_subevent) as EditText
        val editAmt_subevent = payerView_subevent.findViewById<View>(R.id.edit_payers_amt_subevent) as EditText
        val imageClose = payerView_subevent.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(payerView_subevent) }
        layoutList!!.addView(payerView_subevent)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }


}