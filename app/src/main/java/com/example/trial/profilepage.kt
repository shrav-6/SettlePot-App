package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profilepage.*

class profilepage : AppCompatActivity() {

    lateinit var cUser : users
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilepage)

        //readUser()

        backtohomepage.setOnClickListener{
            Toast.makeText(baseContext,"Unsaved changes", Toast.LENGTH_SHORT).show()
            val backtohomeintent = Intent(this, homepageevents::class.java)
            startActivity(backtohomeintent)
            finish()
        }

        saveinfo.setOnClickListener{
            saveUser()                                     //save user deets when edits are made
        }

    }




    private fun saveUser() {
        var uname = username.text.toString().trim()
        var umailid = usermailid.text.toString().trim()
        var uphoneno = userphno.text.toString().trim()
        val curuser = FirebaseAuth.getInstance().currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        val id = curuser!!.uid
        val userobj = users(id,uname,umailid,uphoneno)
        database.child(id).setValue(userobj).addOnCompleteListener {
            Toast.makeText(baseContext,"Updated successfully",Toast.LENGTH_SHORT).show()
        }
    }


}