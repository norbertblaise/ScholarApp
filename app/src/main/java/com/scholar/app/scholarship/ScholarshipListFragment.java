package com.scholar.app.scholarship;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentScholarshipListBinding;
import com.scholar.app.util.FirebaseUtil;

import java.util.ArrayList;

import static com.scholar.app.util.Constants.EMAIL_AUTH_PROVIDER_ID;
import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.Constants.SCHOLARSHIPS;
import static com.scholar.app.util.Constants.STUDENTS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;
import static com.scholar.app.util.FirebaseUtil.mFirebaseAuth;

public class ScholarshipListFragment extends Fragment {

    private FragmentScholarshipListBinding binding;
    public static final String TAG = "ScholarshipListFragment";
    ArrayList<Scholarship> scholarships;

    private ScholarshipFirebaseUiAdapter scholarshipFirebaseUiAdapter;
    private RecyclerView rvScholarships;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        binding = FragmentScholarshipListBinding.inflate(inflater, container, false);
        FirebaseUtil.openFBReference(SCHOLARSHIPS, getActivity());

        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton requestFab = binding.reqScholarshipFab;
        requestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ScholarshipListFragment.this)
                        .navigate(R.id.action_ScholarshipListFragment_to_requestScholarshipFragment);
            }
        });

        //check if onboarding has been completed already
        if (onBoardingFinished()) {

        } //TODO move check if user signed up using email and passwrods to after check for onboarding
        else {
            NavHostFragment.findNavController(ScholarshipListFragment.this)
                    .navigate(R.id.action_ScholarshipListFragment_to_viewPagerFragment);
        }

        if (mFirebaseAuth.getCurrentUser() != null) {
            checkEmailVerification();
            checkProfileExists();


            rvScholarships = binding.rvScholarships;
            //old recyclerview adapter
//        final ScholarshipAdapter scholarshipAdapter = new ScholarshipAdapter();
//        rvScholarships.setAdapter(scholarshipAdapter);


//setup recyclerview with firestoreUI adapter
            setUpRecyclerView();

            LinearLayoutManager scholarshipsLayoutManager =
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvScholarships.setLayoutManager(scholarshipsLayoutManager);

            scholarshipFirebaseUiAdapter.setOnItemClickListener(new ScholarshipFirebaseUiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    Scholarship selectedScholarship = documentSnapshot.toObject(Scholarship.class);
                    ScholarshipListFragmentDirections.ActionScholarshipListFragmentToScholarshipDetailFragment action = ScholarshipListFragmentDirections.actionScholarshipListFragmentToScholarshipDetailFragment(selectedScholarship);
                    NavHostFragment.findNavController(ScholarshipListFragment.this).navigate(action);
                }

                @Override
                public void onPlusOneClick(DocumentSnapshot documentSnapshot, int position) {
                    Scholarship selectedScholarship = documentSnapshot.toObject(Scholarship.class);

                    Petition petition = new Petition(selectedScholarship.getScholarshipId(), currentUser.getUid());
                    //add pettions to firestore
                    firestoreDb.collection(PETITIONS)
                            .add(petition)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "onSuccess: petition added");
                                    //todo add fancy success animation
                                }
                            });
                }
            });

        }
    }

    private void setUpRecyclerView() {
        //TODO add orderby servertimestamp
        Query query = firestoreDb.collection(SCHOLARSHIPS);

        FirestoreRecyclerOptions<Scholarship> options = new FirestoreRecyclerOptions.Builder<Scholarship>()
                .setQuery(query, Scholarship.class)
                .build();

        scholarshipFirebaseUiAdapter = new ScholarshipFirebaseUiAdapter(options);
        rvScholarships.setLayoutManager(new LinearLayoutManager(getContext()));
        rvScholarships.setAdapter(scholarshipFirebaseUiAdapter);
    }

    private Boolean onBoardingFinished() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("Finished", false);
    }

    private void checkEmailVerification() {
        if (currentUser.getProviderId().equals(EMAIL_AUTH_PROVIDER_ID)) {

            if (!currentUser.isEmailVerified()) {
                //sendverification email
                currentUser.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "verification email sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                //open email verification pending screen
                NavHostFragment.findNavController(ScholarshipListFragment.this).navigate(R.id.action_ScholarshipListFragment_to_verificationWaitingFragment);
            }
        }
    }

    private void checkProfileExists() {
        //query students collection for student with id that matches current logged user id
        firestoreDb.collection(STUDENTS)
                .whereEqualTo("studentId", currentUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value == null) {
                            //show addProflieDialog
                            NavHostFragment.findNavController(ScholarshipListFragment.this)
                                    .navigate(R.id.action_ScholarshipListFragment_to_addProfileDialog);

                        }
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.dark_mode_switch).setTitle("Light Mode");
        } else{
            menu.findItem(R.id.dark_mode_switch).setTitle("Dark Mode");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_profile:
                NavHostFragment.findNavController(ScholarshipListFragment.this)
                        .navigate(R.id.action_ScholarshipListFragment_to_StudentProfileFragment);
                return true;

            case R.id.dark_mode_switch:

                if (item.getItemId() == R.id.dark_mode_switch) {
                    // Get the night mode state of the app.
                    int nightMode = AppCompatDelegate.getDefaultNightMode();
                    //Set the theme mode for the restarted activity
                    if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode
                                (AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        AppCompatDelegate.setDefaultNightMode
                                (AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
                // Recreate the activity for the theme change to take effect.
                getActivity().recreate();
                break;


            case R.id.logout_action:
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "onComplete: User logged out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
        if (scholarshipFirebaseUiAdapter != null) {
            scholarshipFirebaseUiAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
        if (scholarshipFirebaseUiAdapter != null) {
            scholarshipFirebaseUiAdapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");

        FirebaseUtil.attachListener();
        //setup recyclerview with firestoreUI adapter
        if(FirebaseUtil.mFirebaseAuth.getCurrentUser() != null){
            setUpRecyclerView();
        }
        if (scholarshipFirebaseUiAdapter != null) {
            scholarshipFirebaseUiAdapter.startListening();
        }




    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
        FirebaseUtil.detachListener();

        if (scholarshipFirebaseUiAdapter != null) {
            scholarshipFirebaseUiAdapter.stopListening();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}