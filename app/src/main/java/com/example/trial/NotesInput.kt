package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.trial.notesclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_notes_input.*
import kotlinx.android.synthetic.main.row_add_notes.*
import java.util.*


class NotesInput : AppCompatActivity(), View.OnClickListener {
    var layoutList: LinearLayout? = null
    var notesList = ArrayList<notesclass>()
    var n_id: String? = null
    private lateinit var dbref : DatabaseReference
//    lateinit var notesobject: notesclass
    var temp_notes_list: MutableList<String> = mutableListOf()
//    lateinit var final_notes_list: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_input)

        var getidfromeventdeetspageintent = intent
        n_id = getidfromeventdeetspageintent.getStringExtra("eventid")

        layoutList = findViewById(R.id.layout_list)

        button_addnotes.setOnClickListener {
            addView()
        }

        button_savenotes.setOnClickListener {
            val result = checkIfValidAndRead()
            dbref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Events")
            //var tempnoteslist = mutableListOf(notesList.toString())
            var notesobject = notesclass(n_id, mutableListOf(temp_notes_list.toString()))
            Toast.makeText(baseContext,"id: $n_id",Toast.LENGTH_LONG).show()
            dbref.child(n_id.toString()).child("Notes").setValue(notesobject).addOnCompleteListener{
//            dbref.setValue(notesobject).addOnCompleteListener{
                Toast.makeText(baseContext,"Notes added",Toast.LENGTH_SHORT).show()
            }
            if(result) {
                Toast.makeText(baseContext, "current notes should display in text view", Toast.LENGTH_SHORT).show()
            }
        }

        backbutton_notesinput.setOnClickListener {
            var backtoeventdeetspageintent = Intent(this, EventActivity::class.java)
            backtoeventdeetspageintent.putExtra("eventnotesid",n_id)
            startActivity(backtoeventdeetspageintent)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_addnotes -> addView()
            R.id.button_savenotes -> if (checkIfValidAndRead()) {
                val intent = Intent(this, ActivityNotes::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", notesList)
                intent.putExtras(bundle)
                startActivity(intent)
            }
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
//                final_notes_list.toMutableList().add(temp_notes_list.toString())
                Toast.makeText(baseContext,temp_notes_list.toString(),Toast.LENGTH_SHORT).show()
            } else {
                result = false
                Toast.makeText(baseContext, "Enter a valid note!", Toast.LENGTH_SHORT).show()
            }
            notesList.add(notes)
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


}
