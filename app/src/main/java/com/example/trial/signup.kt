package com.example.trial

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.*


class signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Signupviaemail.setOnClickListener {

            //above code: To make the user's keyboard disappear after details have been entered on clicking
            //the sign up button so that message can be seen (Successful or unsuccessful)

            if (inputEmail.text.toString().isNullOrEmpty() || inputPassword.text.toString().isNullOrEmpty())

            //if no email or password entered

                signupmessage.text = "Input in fields missing"

            else {
                auth.createUserWithEmailAndPassword(
                        inputEmail.text.toString(),
                        inputPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                signupmessage.text =
                                        "Sign Up Successful"
                                val user = auth.currentUser
                                updateUI(user)
                                val intent = Intent(this, successfulsign::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                signupmessage.text = "Sign Up Failed"
                                updateUI(null)
                            }
                        }
            }
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }
}