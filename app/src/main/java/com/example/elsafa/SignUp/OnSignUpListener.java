package com.example.elsafa.SignUp;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import Model.FirebaseUser;

public interface OnSignUpListener {

    void onVerificationCompleted(PhoneAuthCredential credential);

    void onVerificationFailed(FirebaseException e);

    void onCodeSent(@NonNull String verificationId,
                    @NonNull PhoneAuthProvider.ForceResendingToken token);

    void onGetUserDataSuccess(FirebaseUser firebaseUser);

    void onGetUserDataFailed(Exception e);

    void onSignInFailed();

    void onSignInSuccess();
}
