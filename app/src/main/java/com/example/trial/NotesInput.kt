package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.trial.notesclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_notes_input.*
import kotlinx.android.synthetic.main.row_add_notes.*
import maes.tech.intentanim.CustomIntent.customType
import java.util.*
import kotlin.collections.ArrayList


class NotesInput : AppCompatActivity() {

    companion object {
//        var i: Int = 1
//        var readnotesList = ArrayList<notesclass?>()
        var read_temp_notes_list: MutableList<String> = mutableListOf()
        var notes: String? = null
    }
    var listt : ArrayList<String> = arrayListOf()
//    var note: String?= null
    var layoutList: LinearLayout? = null
    var n_id: String? = null
    var notesList = ArrayList<notesclass>()
    lateinit var notesvaluelist : List<String>
    private lateinit var dbref : DatabaseReference
    private lateinit var getdbref : DatabaseReference
    var notesobject: notesclass? = null

    var temp_notes_list: MutableList<String> = mutableListOf()
//    lateinit var final_notes_list: MutableList<String>
    var flag: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_input)

        var getidfromeventdeetspageintent = intent
        n_id = getidfromeventdeetspageintent.getStringExtra("eventid")
        Log.d("After entering notes activity event id is: ", n_id.toString())
        layoutList = findViewById(R.id.layout_list)


        getdbref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).
        child("Events").child(n_id.toString()).child("Notes").child("notesdata")
        var getnotesdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if (flag == 0) {
                        read_temp_notes_list.clear()
                        read_temp_notes_list = snapshot.getValue() as MutableList<String>
                        for (y in read_temp_notes_list) {
                            listt.add(y)
                        }
                        for (z in listt) {
                            var w = z.split(",")
                            for (g in w) {
                                notes = g.trim('[')
                                notes = notes?.trim(']')
                                notes = notes?.trim()
                                readnotesView()
                            }
                        }
//                    Log.d("READING INSIDE LOOP", read_temp_notes_list.toString())
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Firebase Database Exceptions called - onCancelled(NotesInput)", Toast.LENGTH_SHORT).show()
            }
        }
        getdbref.addValueEventListener(getnotesdata)


        button_addnotes.setOnClickListener {
            addView()
        }

        button_savenotes.setOnClickListener {
            flag=1
            dbref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events").child(n_id.toString())
            dbref.child("Notes").removeValue()
            val result = checkIfValidAndRead()
            if(result) {
                dbref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
                var notesobject = notesclass(n_id, mutableListOf(temp_notes_list.toString()))
                dbref.child(n_id.toString()).child("Notes").setValue(notesobject).addOnCompleteListener {
                    Toast.makeText(baseContext, "Notes added", Toast.LENGTH_SHORT).show()
                }
            }
        }

        backbutton_notesinput.setOnClickListener {
            var backtoeventdeetspageintent = Intent(this, EventActivity::class.java)
            backtoeventdeetspageintent.putExtra("eventnotesid",n_id)
            startActivity(backtoeventdeetspageintent)
            customType(this, "right-to-left")
            finish()
        }
    }


    private fun checkIfValidAndRead(): Boolean {
        notesList.clear()
        temp_notes_list.clear()
        var result = true
        val notes = notesclass()
        for (i in 0 until layoutList!!.childCount) {
            val notesView = layoutList!!.getChildAt(i)
            val editNotesData = notesView.findViewById<View>(R.id.edit_notes_data) as EditText
            if (editNotesData.text.toString() != "") {
                temp_notes_list.add(editNotesData.text.toString())
            } else {
                result = false
                Toast.makeText(baseContext, "Enter a valid note!", Toast.LENGTH_SHORT).show()
            }
//            notesList.add(notes)
        }
        notes.n_id = n_id
        notes.notesdata = temp_notes_list
        return result
    }

    
    private fun addView() {
        val notesView: View = layoutInflater.inflate(R.layout.row_add_notes, null, false)
        val editText = notesView.findViewById<View>(R.id.edit_notes_data) as EditText
        val imageClose = notesView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(notesView) }
        layoutList!!.addView(notesView)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }

    private fun readnotesView() {
        val notesViewx: View = layoutInflater.inflate(R.layout.row_add_notes, null, false)
        val noteswrite = notesViewx.findViewById<View>(R.id.edit_notes_data) as EditText
        noteswrite.setText(notes)
        val imageClose = notesViewx.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removenotesView(notesViewx) }
        layoutList!!.addView(notesViewx)
    }

    private fun removenotesView(view: View) {
        layoutList!!.removeView(view)
    }

}
