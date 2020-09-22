package com.example.elsafa.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elsafa.MainActivity;
import com.example.elsafa.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    TextView text_logout;
    FirebaseAuth auth;
    LoginManager loginManager;

    public SettingsFragment() {
        auth = FirebaseAuth.getInstance();
        loginManager = LoginManager.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        text_logout = root.findViewById(R.id.text_logout);
        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutClicked();
            }
        });

        return root;
    }

    public void logoutClicked() {
        auth.signOut();
        loginManager.logOut();

        FacebookAuthProvider.getCredential(FacebookAuthProvider.FACEBOOK_SIGN_IN_METHOD);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}