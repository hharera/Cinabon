package com.whiteside.cafe.ui.signUp

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.hbb20.CountryCodePicker
import com.whiteside.cafe.R
import com.whiteside.cafe.api.repository.AuthManager
import com.whiteside.cafe.api.repository.UserRepository
import com.whiteside.cafe.databinding.ActivitySignupBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.User
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUp : AppCompatActivity() {
    private lateinit var country_code: CountryCodePicker
    private lateinit var phoneNumber: String
    private lateinit var oldUserID: String

    @Inject
    lateinit var signupPresenter: SignUpPresenter

    @Inject
    lateinit var cartPresenter: CartPresenter

    @Inject
    lateinit var wishListPresenter: WishListPresenter

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var userRepository: UserRepository

    lateinit var bind: ActivitySignupBinding

    private lateinit var user: User
    private lateinit var newUser: User

    private var phoneAuthCredential: PhoneAuthCredential? = null
    private var nameString: String? = null
    private var passwordString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivitySignupBinding.inflate(layoutInflater)
        country_code = findViewById(R.id.country_code)

    }

    fun signUpClicked(view: View?) {
        passwordString = bind.password.text.toString()
        nameString = bind.name.text.toString()
        phoneNumber = "+" +
                country_code.selectedCountryCode +
                "${bind.phoneNumber.text}"

        when (nameString) {
            "" -> bind.name.error = "Name is required"
        }

        when (bind.phoneNumber.text.toString()) {
            "" ->
                Toast.makeText(this, "Phone Number is required", Toast.LENGTH_LONG).show()
        }

        when (passwordString) {
            "" -> bind.password.error = "Password is required"
        }

        signupPresenter.sendVerificationCode(phoneNumber) {
            when (it) {
                "wrong" -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                else -> promptCode(it)
            }
        }
    }

    private fun promptCode(verificationId: String) {
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setView(editText)
            .setCancelable(false)
            .setTitle("Enter Code")
            .setNegativeButton("Cancel") { _, _a -> }
            .setPositiveButton("Submit") { _, _a ->
                val code = editText.text.toString()
                checkCredential(signupPresenter.createCredential(code, verificationId))
            }.show()
    }

    private fun checkCredential(phoneAuthCredential: PhoneAuthCredential) {
        GlobalScope.launch {
            getOldUserData()
            signupPresenter.signInWithPhoneAuthCredential(phoneAuthCredential) {
                when (it) {
                    null -> Toast.makeText(this, "Wrong Verification code", Toast.LENGTH_LONG)
                        .show()
                    else -> set
                }
            }
        }
    }

    suspend fun getOldUserData() {
        GlobalScope.launch {
            val cart = ArrayList<Item>()
            cartPresenter.getCartItems {
                cart.add(it)
            }

            val wishlist = ArrayList<Item>()
            wishListPresenter.getWishList {
                wishlist.add(it)
            }

            val user = signupPresenter.getUser(
                authManager.getCurrentUser().uid
            ) {
                it
            }
        }
    }

    override fun onGetUserDataFailed(e: Exception) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }

    override fun onSignInSuccess() {
        auth.currentUser!!.updatePassword(passwordString!!)
        user.name = (nameString)!!
        user.phoneNumber = (phoneNumber)
        presenter.setNewUserData(user)
        presenter.removeUserData(oldUserID)
    }


    override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
        this.phoneAuthCredential = phoneAuthCredential
        presenter.getCurrentUserData()
    }

}