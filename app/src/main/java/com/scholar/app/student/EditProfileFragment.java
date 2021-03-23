package com.scholar.app.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.scholar.app.R;
import com.scholar.app.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;

    public static final String[] COUNTRIES = new String[]{
            //TODO add list of all countries
    };

    public static final String[] GENDER = new String[]{
            "Male", "Female", "Non-binary", "Prefer not to say"
    };

    public static final String[] CITIES = new String[]{
            //TODO add list of all cities
    };

    public static final String[] COURSES = new String[]{
            //TODO add list of all courses
    };

    public static final String[] DEGREES = new String[]{
            //TODO add list of all degrees
    };


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,CITIES);
        binding.actvCity.setAdapter(cityAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,COUNTRIES);
        binding.actvNationality.setAdapter(countryAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,GENDER);
        binding.actvGender.setAdapter(genderAdapter);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, COURSES);
        binding.actvCourse.setAdapter(coursesAdapter);

        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,DEGREES);
        binding.actvDegreeType.setAdapter(degreesAdapter);

        //university city and country list both come from CITIES and COUNTRIES string arrays
        binding.actvUniCity.setAdapter(cityAdapter);
        binding.actvUniLocation.setAdapter(countryAdapter);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(EditProfileFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}