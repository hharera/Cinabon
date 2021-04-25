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
import com.whiteside.cafe.databinding.ActivitySignupBinding
import com.whiteside.cafe.model.Item
import com.whiteside.cafe.model.SignUpUser
import com.whiteside.cafe.model.User
import com.whiteside.cafe.ui.account.UserPresenter
import com.whiteside.cafe.ui.cart.CartPresenter
import com.whiteside.cafe.ui.wishlist.WishListPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SignUp : AppCompatActivity() {
    private lateinit var country_code: CountryCodePicker
    private lateinit var phoneNumberWithCode: String
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
    lateinit var userPresenter: UserPresenter


    lateinit var bind: ActivitySignupBinding

    private lateinit var oldUser: User
    private val newUser = SignUpUser()


    private var phoneAuthCredential: PhoneAuthCredential? = null

    private var name: String? = null
    private var passwordString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivitySignupBinding.inflate(layoutInflater)
        country_code = findViewById(R.id.country_code)
    }

    fun signUpClicked(view: View) {
        passwordString = bind.password.text.toString()
        name = bind.name.text.toString()
        phoneNumberWithCode = "+" + country_code.selectedCountryCode + "${bind.phoneNumber.text}"
        val phoneNumber = "${bind.phoneNumber.text}"

        when {
            name == "" -> bind.name.error = "Name is required"
            passwordString == "" -> bind.password.error = "Password is required"
            phoneNumber == "" -> Toast.makeText(this, "Phone Number is required", Toast.LENGTH_LONG)
                .show()

            else -> {
                signupPresenter.sendVerificationCode(phoneNumberWithCode) {
                    when (it) {
                        "wrong" -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show()
                        else -> {
                            newUser.name = name!!
                            newUser.verificationCode = it
                            newUser.password = passwordString
                            newUser.phoneNumber = phoneNumberWithCode
                            promptCode()
                        }
                    }
                }
            }
        }
    }

    private fun promptCode() {
        val code = EditText(this)
        code.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setView(code)
            .setCancelable(false)
            .setTitle("Enter Code")
            .setNegativeButton("Cancel") { _, _a -> }
            .setPositiveButton("Submit") { _, _a ->
                newUser.code = code.text.toString()
                handleCode()
            }.show()
    }

    private fun handleCode() {
        GlobalScope.launch {
            async { saveOldData() }.await()
            checkCredential()
        }
    }

    private fun checkCredential() {
        GlobalScope.launch {
            val phoneAuthCredential =
                signupPresenter.createCredential(newUser.code!!, newUser.verificationCode!!)
            signupPresenter.signInWithPhoneAuthCredential(phoneAuthCredential) {
                when (it) {
                    null -> Toast.makeText(
                        this@SignUp,
                        "Wrong Verification code",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> onSignInSuccess()
                }
            }
        }
    }

    private suspend fun saveOldData() {
        GlobalScope.launch {
            val cart = ArrayList<Item>()
            cartPresenter.getCartItems {
                cart.add(it)
            }
            val wishlist = ArrayList<Item>()
            wishListPresenter.getWishList {
                wishlist.add(it)
            }

            signupPresenter.getUser(authManager.getCurrentUser()!!.uid) {
                oldUser = it
            }
            oldUser.cartItems = cart
            oldUser.wishList = wishlist
        }
    }

    fun onGetUserDataFailed(e: Exception) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        e.printStackTrace()
    }

    private fun onSignInSuccess() {
        GlobalScope.launch {
            userPresenter.removeUser(oldUser.uid) {
                userPresenter.addUser(newUser as User) {
                    authManager.updatePassword(newUser.password!!)
                }
            }
        }
    }
}