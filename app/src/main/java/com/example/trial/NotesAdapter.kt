package com.example.trial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class NotesAdapter(notesList: ArrayList<notesclass>) :
    RecyclerView.Adapter<NotesAdapter.NotesView>() {
    var notesList = ArrayList<notesclass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesView {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_notes, parent, false)
        return NotesView(view)
    }

    override fun onBindViewHolder(holder: NotesView, position: Int) {
        val notes = notesList[position]
        holder.textNotesData.text = notes.notesdata.toString()

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class NotesView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textNotesData: TextView


        init {
            textNotesData = itemView.findViewById<View>(R.id.text_notes_data) as TextView

        }
    }

    init {
        this.notesList = notesList
    }
}