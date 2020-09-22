package com.example.elsafa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText password, email;
    TextView guestLogin;
    Button login, sign_up;
    LoginButton facebookLogin;
    ImageView gmail_login;

    LoginManager loginManager;
    DatabaseReference dRef;
    FirebaseAuth auth;
    StorageReference sRef;

    private CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 102;
    private View mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);
        facebookLogin = findViewById(R.id.facebook_login);
        gmail_login = findViewById(R.id.gmail_login);
        guestLogin = findViewById(R.id.guest_login);

        mBinding = new View(this);

        dRef = FirebaseDatabase.getInstance().getReference();
        sRef = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        loginManager = LoginManager.getInstance();

//        getKeyHash();
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (mGoogleSignInClient != null) {
//            goHome();
//        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (auth.getCurrentUser() != null || accessToken != null) {
            goHome();
        }
    }

    public void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.elsafa",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        }
    }

    public void loginClicked(View view) {
        final String password = this.password.getText().toString(),
                username = this.email.getText().toString();

        if (username.equals("")) {
            this.email.setError("E-mail is required");
        } else if (password.equals("")) {
            this.password.setError("Password is required");
        } else {
            stopClicking();
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        goPreView();
                    } else {
                        checkLoginFail();
                    }
                    resetClicking();
                }
            });
        }
    }

    private void goPreView() {
        Intent intent = new Intent(this, PreView.class);
        startActivity(intent);
        finish();
    }

    private void stopClicking() {
        login.setEnabled(false);
        sign_up.setEnabled(false);
        facebookLogin.setEnabled(false);
        gmail_login.setEnabled(false);
        guestLogin.setEnabled(false);
    }

    private void resetClicking() {
        login.setEnabled(true);
        sign_up.setEnabled(true);
        facebookLogin.setEnabled(true);
        gmail_login.setEnabled(true);
        guestLogin.setEnabled(true);
    }

    private void checkLoginFail() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            showInternetError();
        } else {
            Toast.makeText(this, "username or password is not correct", Toast.LENGTH_LONG).show();
        }
    }

    private void showInternetError() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Internet Problem");
        dialog.setMessage("There is an internet problem");

        dialog.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginClicked(login.getRootView());
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

    public void signupClicked(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

    public void guestLogin(View view) {
        stopClicking();
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                            goPreView();
                        } else {
                            Toast.makeText(MainActivity.this, "Cannot login as a guest", Toast.LENGTH_SHORT).show();
                        }
                        resetClicking();
                    }
                });
    }

    public void googleLogin(View view) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void facebookLogin(View view) {
        mCallbackManager = CallbackManager.Factory.create();
        facebookLogin.setReadPermissions("email", "public_profile");
        facebookLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Cannot login with facebook account", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goPreView();
                        } else {
                            Snackbar.make(mBinding, "Cannot login with google account.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void handleFacebookAccessToken(final AccessToken token) {
        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.signInWithCredential(credential);
                    goPreView();
                } else {
                    GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                    if (googleSignInAccount != null) {
                        googleLogin(findViewById(R.id.gmail_login));
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot login with facebook account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}