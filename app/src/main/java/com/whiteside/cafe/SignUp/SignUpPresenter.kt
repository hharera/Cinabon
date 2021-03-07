package com.whiteside.cafe.SignUp

import Model.FirebaseUser
import Model.Item
import Model.User
import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit

class SignUpPresenter(listener: OnSignUpListener?, activity: Activity?) {
    private val auth: FirebaseAuth?
    private val listener: OnSignUpListener?
    private val activity: Activity?
    private val fStore: FirebaseFirestore?
    fun sendVerificationCode(phoneNumber: String?) {
        val mCallbacks: OnVerificationStateChangedCallbacks =
            object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
//                        listener.onVerificationCompleted(credential);
                }

                override fun onVerificationFailed(e: FirebaseException?) {
                    listener.onVerificationFailed(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: ForceResendingToken
                ) {
                    listener.onCodeSent(verificationId, token)
                    Log.d("onCodeSent", token.toString())
                }
            }
        auth.useAppLanguage()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential?) {
        auth.signOut()
        auth.signInWithCredential(credential)
            .addOnSuccessListener { listener.onSignInSuccess() }
            .addOnFailureListener { listener.onSignInFailed() }
    }

    fun getCurrentUserData() {
        val user = FirebaseUser()
        val cart: MutableList<Item?> = ArrayList()
        val wishList: MutableList<Item?> = ArrayList()
        val getUser: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .get()
                    .addOnSuccessListener { ds ->
                        user.name = ds.getString("name")
                        user.phoneNumber = ds.getString("phoneNumber")
                        user.cartItems = cart
                        user.wishList = wishList
                        listener.onGetUserDataSuccess(user)
                    }
                    .addOnFailureListener { e -> listener.onGetUserDataFailed(e) }
            }
        }
        val getCart: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (ds in querySnapshot.documents) {
                            cart.add(ds.toObject(Item::class.java))
                        }
                        getUser.start()
                    }
            }
        }
        val getWishList: Thread = object : Thread() {
            override fun run() {
                fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (ds in querySnapshot.documents) {
                            wishList.add(ds.toObject(Item::class.java))
                        }
                        getCart.start()
                    }
            }
        }
        getWishList.start()
    }

    fun setNewUserData(firebaseUser: FirebaseUser?) {
        firebaseUser.setUID(auth.getUid())
        val user = User()
        user.uid = firebaseUser.getUID()
        user.name = firebaseUser.getName()
        user.phoneNumber = firebaseUser.getPhoneNumber()
        fStore.collection("Users")
            .document(auth.getUid())
            .set(user)
        for (item in firebaseUser.getCartItems()) {
            fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .document()
                .set(item)
        }
        for (item in firebaseUser.getWishList()) {
            fStore.collection("Users")
                .document(auth.getUid())
                .collection("WishList")
                .document()
                .set(item)
        }
    }

    fun removeUserData(oldUserID: String?) {
        fStore.collection("Users")
            .document(oldUserID)
            .delete()
        fStore.collection("Users")
            .document(oldUserID)
            .collection("Cart")
            .get()
            .addOnSuccessListener { qs ->
                for (ds in qs.documents) {
                    fStore.document(ds.reference.path).delete()
                }
            }
        fStore.collection("Users")
            .document(oldUserID)
            .collection("WishList")
            .get()
            .addOnSuccessListener { qs ->
                for (ds in qs.documents) {
                    fStore.document(ds.reference.path).delete()
                }
            }
    }

    init {
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        this.activity = activity
        this.listener = listener
    }
}