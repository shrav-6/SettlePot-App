package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nonpayers_input_subevent.*
import kotlinx.android.synthetic.main.activity_nonpayers_input.*
import java.util.ArrayList

class NonPayersInputSubevents : AppCompatActivity(), View.OnClickListener {
    var layoutList: LinearLayout? = null

    var nonpayersList_subevent = ArrayList<NonPayers_subevent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nonpayers_input_subevent)
        layoutList = findViewById(R.id.layout_list)

        val intentcaller = intent

        button_addnonpayers_subevent.setOnClickListener {
            addView()
        }
        button_createrolesfornonpayers_subevent.setOnClickListener {
            val result = checkIfValidAndRead()
            if(result) {
                val intent = Intent(this, ConfirmRoles_subevent::class.java)
                startActivity(intent)
                finish()
            }
        }

        backbutton_nonpayersinput_subevent.setOnClickListener {

            if(intentcaller.hasExtra("callerfromboth")) {
                val intentcallfromnonpayersinput_subevent = Intent(this, PayersInputSubevents::class.java)
                intentcallfromnonpayersinput_subevent.putExtra("callerfromboth", "callerfromboth")
                startActivity(intentcallfromnonpayersinput_subevent)
                finish()
            } else {
                val intent = Intent(this, rolesSubevent::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_createrolesfornonpayers_subevent -> if (checkIfValidAndRead()) {
                val intent = Intent(this, NonPayers_subevent::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", nonpayersList_subevent)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }


    private fun checkIfValidAndRead(): Boolean {
        nonpayersList_subevent.clear()
        var result = true
        var nonpayercount_subevent = 1
        for (i in 0 until layoutList!!.childCount) {
            val nonpayerView_subevent = layoutList!!.getChildAt(i)
            val editNonPayersName_subevent = nonpayerView_subevent.findViewById<View>(R.id.edit_nonpayers_name_subevent) as EditText

            val nonpayer_subevent = NonPayers_subevent()
            if (editNonPayersName_subevent.text.toString() != "") {
                nonpayer_subevent.nonpayerName_subevent = editNonPayersName_subevent.text.toString()
            } else {
                nonpayer_subevent.nonpayerName_subevent= "NonPayer $nonpayercount_subevent"
                Toast.makeText(baseContext, nonpayer_subevent.nonpayerName_subevent, Toast.LENGTH_SHORT).show()
                nonpayercount_subevent++
            }

            nonpayersList_subevent.add(nonpayer_subevent)
        }

        return result
    }

    private fun addView() {
        val nonpayerView_subevent: View = layoutInflater.inflate(R.layout.row_add_nonpayer_subevent, null, false)
        val editText = nonpayerView_subevent.findViewById<View>(R.id.edit_nonpayers_name_subevent) as EditText
        val imageClose = nonpayerView_subevent.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(nonpayerView_subevent) }
        layoutList!!.addView(nonpayerView_subevent)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }


}