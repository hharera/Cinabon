package com.whiteside.cafe.SignUp;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Model.FirebaseUser;
import Model.Item;
import Model.User;

public class SignUpPresenter {


    private final FirebaseAuth auth;
    private final OnSignUpListener listener;
    private final Activity activity;
    private final FirebaseFirestore fStore;


    public SignUpPresenter(OnSignUpListener listener, Activity activity) {
        fStore = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.listener = listener;
    }

    public void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        listener.onVerificationCompleted(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        listener.onVerificationFailed(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        listener.onCodeSent(verificationId, token);
                        Log.d("onCodeSent", token.toString());
                    }
                };

        auth.useAppLanguage();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signOut();
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        listener.onSignInSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onSignInFailed();
                    }
                });
    }

    public void getCurrentUserData() {
        FirebaseUser user = new FirebaseUser();
        List<Item> cart = new ArrayList<>();
        List<Item> wishList = new ArrayList<>();


        Thread getUser = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot ds) {
                                user.setName(ds.getString("name"));
                                user.setPhoneNumber(ds.getString("phoneNumber"));
                                user.setCartItems(cart);
                                user.setWishList(wishList);
                                listener.onGetUserDataSuccess(user);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onGetUserDataFailed(e);
                            }
                        });
            }
        };

        Thread getCart = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("Cart")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                                    cart.add(ds.toObject(Item.class));
                                }
                                getUser.start();
                            }
                        });
            }
        };

        Thread getWishList = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("WishList")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                                    wishList.add(ds.toObject(Item.class));
                                }
                                getCart.start();
                            }
                        });
            }
        };
        getWishList.start();
    }

    public void setNewUserData(FirebaseUser firebaseUser) {
        firebaseUser.setUID(auth.getUid());

        User user = new User();
        user.setUID(firebaseUser.getUID());
        user.setName(firebaseUser.getName());
        user.setPhoneNumber(firebaseUser.getPhoneNumber());

        fStore.collection("Users")
                .document(auth.getUid())
                .set(user);

        for (Item item : firebaseUser.getCartItems()) {
            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document()
                    .set(item);
        }

        for (Item item : firebaseUser.getWishList()) {
            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document()
                    .set(item);
        }
    }

    public void removeUserData(String oldUserID) {
        fStore.collection("Users")
                .document(oldUserID)
                .delete();

        fStore.collection("Users")
                .document(oldUserID)
                .collection("Cart")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qs) {
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            fStore.document(ds.getReference().getPath()).delete();
                        }
                    }
                });

        fStore.collection("Users")
                .document(oldUserID)
                .collection("WishList")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qs) {
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            fStore.document(ds.getReference().getPath()).delete();
                        }
                    }
                });
    }
}
