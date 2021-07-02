package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Loginviaemail.setOnClickListener{

            if (inputEmailLogin.text.toString().isNullOrEmpty() || inputPasswordLogin.text.toString().isNullOrEmpty()) {    //if no email or password entered
                Toast.makeText(this, "Input(s) missing", Toast.LENGTH_SHORT).show()
                Log.d("Loginviaemail","Input missing")
            }

            auth.signInWithEmailAndPassword(inputEmailLogin.text.toString().trim(), inputPasswordLogin.text.toString().trim())
                .addOnCompleteListener(this) { task ->     //can give any name instead of task
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Loginviaemail", "LoginWithEmail:success")
                        Toast.makeText(baseContext, "Logged in successfully",Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        updateUI(user)
                        val loginviaemailintent = Intent(this, successfulsign::class.java)
                        startActivity(loginviaemailintent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Loginviaemail", "LoginWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }

        }
        backtologindeetspage.setOnClickListener {
            val backtologindeetspageintent = Intent(this, logindeetspage::class.java)
            startActivity(backtologindeetspageintent)
            finish()
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }
}