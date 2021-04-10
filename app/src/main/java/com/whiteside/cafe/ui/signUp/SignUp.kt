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
import com.whiteside.cafe.model.User

class SignUp : AppCompatActivity(), OnSignUpListener {
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var country_code: CountryCodePicker
    private lateinit var sign_up: Button
    private lateinit var phoneNumber: String
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var oldUserID: String
    private lateinit var presenter: SignUpPresenter
    private lateinit var user: User
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
        oldUserID = auth.uid!!
    }

    fun signUpClicked(view: View?) {
        passwordString = password.text.toString()
        nameString = name.text.toString()

        if (name.equals("")) {
            name.error = "Name is required"
        } else if ("" == phone.text.toString()) {
            Toast.makeText(this, "Phone Number is required", Toast.LENGTH_LONG).show()
        } else if ("" == passwordString) {
            password.error = "Password is required"
        } else {
            phoneNumber = "+" + country_code.selectedCountryCode + phone.text.toString()
            nameString = name.text.toString()
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
            phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId!!, code)
            presenter.getCurrentUserData()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onGetUserDataSuccess(user: User) {
        this.user = user
        presenter.signInWithPhoneAuthCredential(phoneAuthCredential!!)
    }

    override fun onGetUserDataFailed(e: Exception) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }

    override fun onSignInFailed() {
        Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_LONG).show()
    }

    override fun onSignInSuccess() {
        auth.currentUser!!.updatePassword(passwordString!!)
        user.name = (nameString)!!
        user.phoneNumber = (phoneNumber)
        presenter.setNewUserData(user)
        presenter.removeUserData(oldUserID)
    }

    override fun onCodeSent(verificationId: String, forceResendingToken: ForceResendingToken) {
        promptCode(verificationId)
    }

    override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
        this.phoneAuthCredential = phoneAuthCredential
        presenter.getCurrentUserData()
    }

    override fun onVerificationFailed(e: FirebaseException?) {
        e?.printStackTrace()
    }
}