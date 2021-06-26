package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event_activity.*

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event_activity)
        eventpagebackbutton.setOnClickListener {
            val intent = Intent(this, homepageevents::class.java)
            startActivity(intent)
        }

        editeventprofile.setOnClickListener {
            val intent = Intent(this, EditEventProfile::class.java)
            startActivity(intent)
        }

        var eventname = eventName.text.toString()

        notesbutton.setOnClickListener {
            print("go to notes activity")
        }

        viewrolesbutton.setOnClickListener {
            print("go to database and view roles")
        }

        viewsubeventsbutton.setOnClickListener {
            print("view sub events")
        }

        addrolesbutton.setOnClickListener {
            val intent = Intent(this, RolesPage::class.java)
            startActivity(intent)
        }

        addsubeventsbutton.setOnClickListener {
            print("add sub events activity part")
        }

    }
}