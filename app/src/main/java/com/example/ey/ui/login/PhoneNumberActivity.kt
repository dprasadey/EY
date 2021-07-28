package com.example.ey.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.ey.R
import com.example.ey.databinding.ActivityPhoneNumberBinding
import com.example.ey.ui.Otp.OtpActivity
import com.example.ey.ui.main.MainActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class PhoneNumberActivity : AppCompatActivity() {

    private var _binding: ActivityPhoneNumberBinding? = null
    private val binding get() = _binding!!

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PhoneNumberViewModel::class.java)
    }

    // this stores the phone number of the user
    var number: String = ""

    // create instance of firebase auth
    lateinit var auth: FirebaseAuth

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        auth = FirebaseAuth.getInstance()

        //xml edit text change listener
        binding.etPhoneNumber.apply {

            afterTextChanged {
                viewModel.onEmailChange(it)
            }
        }

        viewModel.PhnNumberField.observe(this, {
            if (binding.etPhoneNumber.text.toString() != it) binding.etPhoneNumber.setText(it)
        })

        viewModel.login.observe(this, {
            if (it) {
                sendVerificationCode("+91" + binding.etPhoneNumber.text.toString());
            }
        })
        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("GFG", "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG", "onVerificationFailed $e")
            }

            // On code is sent by the firebase this method is called
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext, OtpActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    fun enableSubmitIfReady() {
        val isReady: Boolean =
            findViewById<EditText>(R.id.et_phone_number).getText().toString().length > 3
        findViewById<Button>(R.id.button_otp).setEnabled(isReady)
        findViewById<Button>(R.id.button_otp).setBackgroundColor(getResources().getColor(R.color.button))

    }

    // this method sends the verification code
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
                enableSubmitIfReady()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
