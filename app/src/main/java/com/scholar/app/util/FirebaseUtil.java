package com.scholar.app.util;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.scholar.app.scholarship.Scholarship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.scholar.app.util.Constants.SCHOLARSHIPS;

public class FirebaseUtil {
    private static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseFirestore firestoreDb;
    public static CollectionReference collectionReference;
    public static DocumentReference mStudentsDocumentRef;
    public static DocumentReference mScholarshipsDocumentRef;
    public static DocumentReference mPetittionsDocumentRef;
    public static ArrayList<Scholarship> mScholarships;
    public static FirebaseUser currentUser;



    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static FirebaseAuth.AuthStateListener mEmailVerified;
    public static final int RC_SIGN_IN = 123;
    private static Activity caller;

    private FirebaseUtil() {
    }


    public static void openFBReference(String ref, Activity callerActivity) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firestoreDb = FirebaseFirestore.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            if (mFirebaseAuth.getCurrentUser() != null){
                currentUser = mFirebaseAuth.getCurrentUser();
            }
            mScholarships = new ArrayList<Scholarship>();

            caller = callerActivity;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                    }
                    Toast.makeText(callerActivity.getBaseContext(), "Welcome back!", Toast.LENGTH_LONG).show();


                }
            };




        }
        mScholarshipsDocumentRef = firestoreDb.collection(SCHOLARSHIPS).document();
        collectionReference = firestoreDb.collection(ref);
//    mCollectionReference = mFirebaseFirestore.collection("customers");
    }


    public static void signIn() {
        //Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );


        //Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static void attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public static void detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }


}
