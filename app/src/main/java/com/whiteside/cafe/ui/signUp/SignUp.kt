package com.whiteside.cafe.ui.signUp

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.firestore.FirebaseFirestore
import com.hbb20.CountryCodePicker
import com.whiteside.cafe.R
import com.whiteside.cafe.model.FirebaseUser

class SignUp : AppCompatActivity(), OnSignUpListener {
    private var name: EditText? = null
    private var password: EditText? = null
    private var phone: EditText? = null
    private var country_code: CountryCodePicker? = null
    private var sign_up: Button? = null
    private var phoneNumber: String? = null
    private var auth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var oldUserID: String? = null
    private var presenter: SignUpPresenter? = null
    private var firebaseUser: FirebaseUser? = null
    private var phoneAuthCredential: PhoneAuthCredential? = null
    private var nameString: String? = null
    private var passwordString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        phone = findViewById(R.id.phone_number)
        country_code = findViewById(R.id.country_code)
        name = findViewById(R.id.name)
        password = findViewById(R.id.password)
        sign_up = findViewById(R.id.sign_up)
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        presenter = SignUpPresenter(this, this)
        oldUserID = auth.getUid()
    }

    fun signUpClicked(view: View?) {
        passwordString = password.getText().toString()
        nameString = name.getText().toString()
        if ("" == name) {
            name.setError("Name is required")
        } else if ("" == phone.getText().toString()) {
            Toast.makeText(this, "Phone Number is required", Toast.LENGTH_LONG).show()
        } else if ("" == passwordString) {
            password.setError("Password is required")
        } else {
            phoneNumber = "+" + country_code.getSelectedCountryCode() + phone.getText().toString()
            nameString = name.getText().toString()
            presenter.sendVerificationCode(phoneNumber)
        }
    }

    private fun promptCode(verificationId: String?) {
        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(editText)
        builder.setCancelable(false)
        builder.setTitle("Enter Code")
        builder.setPositiveButton("Submit") { dialog, which ->
            val code = editText.text.toString()
            phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code)
            presenter.getCurrentUserData()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onGetUserDataSuccess(firebaseUser: FirebaseUser?) {
        this.firebaseUser = firebaseUser
        presenter.signInWithPhoneAuthCredential(phoneAuthCredential)
    }

    override fun onGetUserDataFailed(e: Exception?) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }

    override fun onSignInFailed() {
        Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_LONG).show()
    }

    override fun onSignInSuccess() {
        auth.getCurrentUser().updatePassword(passwordString)
        firebaseUser.setName(nameString)
        firebaseUser.setPhoneNumber(phoneNumber)
        presenter.setNewUserData(firebaseUser)
        presenter.removeUserData(oldUserID)
    }

    override fun onCodeSent(verificationId: String, forceResendingToken: ForceResendingToken) {
        promptCode(verificationId)
    }

    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
        this.phoneAuthCredential = phoneAuthCredential
        presenter.getCurrentUserData()
    }

    override fun onVerificationFailed(e: FirebaseException) {
//        Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }
}