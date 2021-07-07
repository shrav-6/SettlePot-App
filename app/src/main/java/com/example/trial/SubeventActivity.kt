package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_event_activity.*
import kotlinx.android.synthetic.main.activity_subevents.*

class SubeventActivity : AppCompatActivity() {

    companion object{
        var subeventnamecounter: Int = 0
    }
    lateinit var subevent: subevents
    private lateinit var subref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subevents)


        var sid: String? = null
        var eid: String? = null
        var counter:Int = 0
        val receivesubeventid = intent
        if(receivesubeventid.hasExtra("newsubeventid"))
        {
            eid = receivesubeventid.getStringExtra("eventforsub_id")
            sid = receivesubeventid.getStringExtra("newsubeventid")
//            counter = receivesubeventid.getIntExtra("counterforsubeventname",0)
        }

//        if(counter == 1)
//        {
//            subeventnamecounter = 0
//        }

        backbutton_subevents.setOnClickListener{
            val back_intent=Intent(this, EventActivity::class.java)
            back_intent.putExtra("eventidbackfromsubevent",eid)
//            back_intent.putExtra("subeventidbackfromsubevent",sid)
            startActivity(back_intent)
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
        viewroles_subevents.setOnClickListener {
            print("roles existing")
        }

        addroles_subevents.setOnClickListener {
            val addrolessubevents_intent=Intent(this, rolesSubevent::class.java)
            addrolessubevents_intent.putExtra("Currenteventid",eid)
            addrolessubevents_intent.putExtra("Currentsubeventid",sid)
            startActivity(addrolessubevents_intent)
            finish()
        }

        close_subevent.setOnClickListener {
            print("closing subevent and go to event page")
        }
    }
}