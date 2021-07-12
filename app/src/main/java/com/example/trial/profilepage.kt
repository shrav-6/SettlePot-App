package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profilepage.*
import maes.tech.intentanim.CustomIntent.customType
import com.example.trial.users as users1

class profilepage : AppCompatActivity() {

    lateinit var cUser : users1
    private lateinit var database: DatabaseReference
    private lateinit var readforprofileref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilepage)

        //go to home page activity
        backtohomepage.setOnClickListener{
            val backtohomeintent = Intent(this, homepageevents::class.java)
            startActivity(backtohomeintent)
            customType(this,"bottom-to-top")
            finish()
        }

        //save user information
        saveinfo.setOnClickListener{
            saveUser()
        }

        //read previously saved user's information from firebase database
        readforprofileref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Profile info")
        var readprofileinfo = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    cUser = snapshot.getValue(users1::class.java)!!
                    username.setText(cUser?.name.toString())
                    usermailid.setText(cUser?.mailid.toString())
                    userphno.setText(cUser?.phoneno.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        readforprofileref.addValueEventListener(readprofileinfo)
    }

    //save user info
    private fun saveUser() {
        var uname = username.text.toString().trim()
        var umailid = usermailid.text.toString().trim()
        var uphoneno = userphno.text.toString().trim()
        val curuser = FirebaseAuth.getInstance().currentUser
        database = FirebaseDatabase.getInstance().getReference("Users")
        val id = curuser!!.uid
        val userobj = users1(id,uname,umailid,uphoneno)
        database.child(id).child("Profile info").setValue(userobj).addOnCompleteListener {
            Toast.makeText(baseContext,"Updated successfully",Toast.LENGTH_SHORT).show()
        }
    }

}