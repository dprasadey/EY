package com.example.ey.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider

class PhoneNumberViewModel : ViewModel() {

    val PhnNumberField: MutableLiveData<String> by lazy { MutableLiveData() }
    val login: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    // this stores the phone number of the user
    var number: String = ""

    //Called Function for all the edittext text change from UI
    fun onEmailChange(email: String) = PhnNumberField.postValue(email)
    //login click event..
    fun onLogin() {
        login.postValue(true)
        number = PhnNumberField.value.toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()) {
            number = "+91$number"
            login.postValue(true)
        } else {
            login.postValue(false)
        }

    }
}