package com.scholar.app.scholarship;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.scholar.app.R;
import com.scholar.app.databinding.FragmentRequestScholarshipBinding;


public class RequestScholarshipFragment extends Fragment {
    private FragmentRequestScholarshipBinding binding;



    public RequestScholarshipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestScholarshipBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        EditText requestTitle = binding.scholarshipReqTitle;
        EditText requestDescription = binding.scholarshipReqDescription;
        EditText requestAmount = binding.scholarshipReqAmount;
        Button requestButton = binding.scholarshipReqButton;



        return view;
    }
}