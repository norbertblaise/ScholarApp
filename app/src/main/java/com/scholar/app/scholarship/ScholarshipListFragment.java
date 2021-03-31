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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentScholarshipListBinding;
import com.scholar.app.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.Objects;

import static com.scholar.app.util.Constants.SCHOLARSHIPS;
import static com.scholar.app.util.Constants.STUDENTS;

public class ScholarshipListFragment extends Fragment {

    private FragmentScholarshipListBinding binding;
    public static final String TAG = "ScholarshipListFragment";
    ArrayList<Scholarship> scholarships;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        binding = FragmentScholarshipListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //check if onboarding has been completed already
        if (onBoardingFinished()) {

        } else {
            NavHostFragment.findNavController(ScholarshipListFragment.this)
                    .navigate(R.id.action_ScholarshipListFragment_to_viewPagerFragment);
        }
        FirebaseUtil.openFBReference(SCHOLARSHIPS, getActivity());

        RecyclerView rvScholarships = binding.rvScholarships;
        final ScholarshipAdapter scholarshipAdapter = new ScholarshipAdapter();
        rvScholarships.setAdapter(scholarshipAdapter);

        LinearLayoutManager scholarshipsLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvScholarships.setLayoutManager(scholarshipsLayoutManager);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ScholarshipListFragment.this)
                        .navigate(R.id.action_ScholarshipListFragment_to_ScholarshipDetailFragment);
            }
        });
    }

    private Boolean onBoardingFinished() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("Finished", false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_action:
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "onComplete: User logged out");
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
            case R.id.action_settings:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }
}