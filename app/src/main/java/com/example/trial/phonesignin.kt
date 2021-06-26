package com.example.trial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phonesignin.*
import java.util.concurrent.TimeUnit

class phonesignin : AppCompatActivity() {

    var number : String = "" //stores the phone number of the user

    lateinit var auth: FirebaseAuth //creating instance of firebase auth

    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var flag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phonesignin)

        backtosignindeetspagephone.setOnClickListener {
            val backtosignindeetspagephoneintent = Intent(this, signupdeetspage::class.java)
            startActivity(backtosignindeetspagephoneintent)
            finish()
        }

        auth=FirebaseAuth.getInstance()

        buttonsendotp.setOnClickListener{
            login() //calling function for verification
            if(flag==0){
                flag=1
                object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        resendotptext.setText("seconds remaining: " + millisUntilFinished / 1000)
                    }

                    override fun onFinish() {
                        resendotptext.setText("Click on Send OTP again to resend")
                        flag = 0
                    }
                }.start()
            }
            else{
                Toast.makeText(applicationContext,"Please wait",Toast.LENGTH_SHORT)
            }

        }

        callbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks() {    //callback function for phone auth

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {     //when verification is completed
                startActivity(Intent(applicationContext, successfulsign::class.java))
                finish()
                Log.d("phonesignin", "onVerificationCompleted Success")
            }

            override fun onVerificationFailed(e: FirebaseException) {       //if verification fails, add log statement to see the exception
                Log.d("phonesignin", "onVerificationFailed $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("phonesignin", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token                     //storing token and verification id

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // use this id to send the otp back to firebase
                val intent = Intent(applicationContext, otpconfirm::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun login() {
        number = enterphonenumber.text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()){
            number = "+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }
    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit to resend
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)     //reentrant method - no matter how many times you click on send button within a min, otp is sent only once - the same otp
        Log.d("phonesignin" , "Auth started")
    }
}