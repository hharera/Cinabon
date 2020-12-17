package com.example.elsafa.SignUp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elsafa.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import Model.FirebaseUser;

public class SignUp extends AppCompatActivity
        implements OnSignUpListener {

    private EditText name, password, phone;
    private CountryCodePicker country_code;
    private Button sign_up;

    private String phoneNumber;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private String oldUserID;
    private SignUpPresenter presenter;
    private FirebaseUser firebaseUser;
    private PhoneAuthCredential phoneAuthCredential;
    private String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phone = findViewById(R.id.phone_number);
        country_code = findViewById(R.id.country_code);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.sign_up);


        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        presenter = new SignUpPresenter(this, this);
        oldUserID = auth.getUid();
    }


    public void signUpClicked(View view) {
        final String password = this.password.getText().toString(),
                name = this.name.getText().toString();

        if ("".equals(name)) {
            this.name.setError("Name is required");
        } else if ("".equals(phone.getText().toString())) {
            Toast.makeText(this, "Phone Number is required", Toast.LENGTH_LONG).show();
        } else if ("".equals(password)) {
            this.password.setError("Password is required");
        } else {
            phoneNumber = "+" + this.country_code.getSelectedCountryCode() + this.phone.getText().toString();
            nameString = this.name.getText().toString();
            presenter.sendVerificationCode(phoneNumber);
        }
    }

    private void promptCode(String verificationId) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(editText);
        builder.setCancelable(false);
        builder.setTitle("Enter Code");
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = editText.getText().toString();
                phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
                presenter.getCurrentUserData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    @Override
    public void onGetUserDataSuccess(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
        presenter.signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    @Override
    public void onGetUserDataFailed(Exception e) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    @Override
    public void onSignInFailed() {
        Toast.makeText(this, "Invalid Verification Code", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInSuccess() {
        auth.getCurrentUser().updatePassword(password.getText().toString());
        firebaseUser.setName(nameString);
        firebaseUser.setPhoneNumber(phoneNumber);
        presenter.setNewUserData(firebaseUser);
        presenter.removeUserData(oldUserID);
    }

    @Override
    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        promptCode(verificationId);
    }

    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        this.phoneAuthCredential = phoneAuthCredential;
        presenter.getCurrentUserData();
    }

    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
//        Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

}