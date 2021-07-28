package com.example.ey.ui.Otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.ey.ui.main.MainActivity
import com.example.ey.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mukesh.OtpView

class OtpActivity : AppCompatActivity() {
    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(OtpViewModel::class.java)
    }


    // get reference of the firebase auth
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        auth=FirebaseAuth.getInstance()

        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")

        val otpView: OtpView
        otpView = findViewById(R.id.otp_view)

        otpView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(arg0: Editable) {
                enableSubmitIfReady()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        // fill otp and call the on click on button
        findViewById<Button>(R.id.button_otp).setOnClickListener {
            val otp = otpView.text.toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun enableSubmitIfReady() {
        val isReady: Boolean = findViewById<OtpView>(R.id.otp_view).getText().toString().length > 3
        findViewById<Button>(R.id.button_otp).setEnabled(isReady)
        findViewById<Button>(R.id.button_otp).setBackgroundColor(getResources().getColor(R.color.button))

    }
    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
