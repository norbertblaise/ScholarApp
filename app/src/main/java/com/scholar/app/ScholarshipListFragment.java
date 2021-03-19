package com.scholar.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ScholarshipListFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //check if onboarding has been completed already
        if (!onBoardingFinished()){
            NavHostFragment.findNavController(ScholarshipListFragment.this)
                    .navigate(R.id.action_ScholarshipListFragment_to_viewPagerFragment);
        }

        
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_scholarship_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ScholarshipListFragment.this)
                        .navigate(R.id.action_ScholarshipDetailFragment_to_ScholarshipListFragment);
            }
        });
    }

    private Boolean onBoardingFinished(){
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("Finished", false);
    }

}