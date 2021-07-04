package com.example.trial

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notes.*
import java.util.*


class ActivityNotes : AppCompatActivity() {

    var notesList: ArrayList<notesclass> = ArrayList()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        recycler_notes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        notesList = intent.extras!!.getSerializable("list") as ArrayList<notesclass>
        recycler_notes.setAdapter(NotesAdapter(notesList))
    }
}