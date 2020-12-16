package com.example.elsafa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private EditText name, password, phone;
    private CountryCodePicker country_code;
    private Button sign_up;

    private String phoneNumber;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private String currentUserId;

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
            sendVerificationCode();
        }
    }

    private void sendVerificationCode() {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(SignUp.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        promptCode(verificationId);
                        Log.d("onCodeSent", token.toString());
                    }
                };

        auth.useAppLanguage();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        currentUserId = auth.getUid();
        auth.signOut();
        auth.signInWithCredential(credential);
        addPhoneNumberToFirebase();
    }

    private void addPhoneNumberToFirebase() {
        fStore.collection("Users")
                .document(currentUserId)
                .update("phoneNumber", phoneNumber);
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
                checkCode(verificationId, editText.getText().toString());
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

    private void checkCode(String verificationId, String code) {
        AuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        currentUserId = auth.getUid();
//        auth.signOut();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });
    }

    private void internetError() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Internet Problem");
        dialog.setMessage("There is an internet problem");

        dialog.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signUpClicked(sign_up.getRootView());
                dialog.cancel();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private void accountIsRegisteredError() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Account is already registered");
        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                goLogin();
            }
        });

        dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    private void goLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void succeedRegister() {
        Toast.makeText(this, "successful register", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        auth.signOut();
        startActivity(intent);
        finish();
    }
}