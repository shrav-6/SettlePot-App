package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_payers_input.*
import java.util.*


class PayersInput : AppCompatActivity() , View.OnClickListener {
    var layoutList: LinearLayout? = null

    var amtList: MutableList<String?> = ArrayList()
    var payersList = ArrayList<Payers>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payers_input)
        layoutList = findViewById(R.id.layout_list)

        val intentcaller = intent

        button_addpayers.setOnClickListener {
            addView()
        }
        button_createrolesforpayers.setOnClickListener {
            val result = checkIfValidAndRead()

            if(result) {
                if(intentcaller.hasExtra("callerfromboth")) {
                    val intentcallfrompayersinput = Intent(this, NonPayersInput::class.java)
                    intentcallfrompayersinput.putExtra("callerfromboth", "callerfromboth")
                    startActivity(intentcallfrompayersinput)
                    finish()
                }
                else {
                    val intent = Intent(this, ConfirmRoles::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        backbutton_payersinput.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_createrolesforpayers -> if (checkIfValidAndRead()) {
                val intent = Intent(this, ActivityPayers::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", payersList)
                intent.putExtras(bundle)
                startActivity(intent)
            }
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
                Toast.makeText(baseContext, payer.payerName, Toast.LENGTH_SHORT).show()
                payercount++
            }
            if (editPayersAmt.text.toString() != "") {
                payer.payerAmt = editPayersName.text.toString()
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
        val editText = payerView.findViewById<View>(R.id.edit_payers_name) as EditText
        val editAmt = payerView.findViewById<View>(R.id.edit_payers_amt) as EditText
        val imageClose = payerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(payerView) }
        layoutList!!.addView(payerView)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }


}