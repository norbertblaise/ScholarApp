package com.scholar.app.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.scholar.app.R;
import com.scholar.app.databinding.FragmentEditProfileBinding;

import static com.scholar.app.util.Constants.CITIES;
import static com.scholar.app.util.Constants.COUNTRIES;
import static com.scholar.app.util.Constants.COURSES;
import static com.scholar.app.util.Constants.DEGREES;
import static com.scholar.app.util.Constants.GENDER;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    AutoCompleteTextView country;
    AutoCompleteTextView city;
    AutoCompleteTextView gender;
    AutoCompleteTextView course;
    AutoCompleteTextView degree;
    AutoCompleteTextView uniCity;
    AutoCompleteTextView uniLocation;
    EditText fName;
    EditText lName;
    EditText dob;
    EditText startDate;
    EditText expectedGrad;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        country = binding.actvNationality;
        city = binding.actvCity;
        gender = binding.actvGender;
        course = binding.actvCourse;
        degree = binding.actvDegreeType;
        uniCity = binding.actvUniCity;
        uniLocation = binding.actvUniLocation;
        fName = binding.editTextFName;
        lName = binding.editTextLName;
        dob = binding.editTextDob;
        startDate = binding.editTextStartDate;
        expectedGrad = binding.editTextDateExpectedGrad;

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, CITIES);
        city.setAdapter(cityAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, COUNTRIES);
        country.setAdapter(countryAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, GENDER);
        gender.setAdapter(genderAdapter);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, COURSES);
        course.setAdapter(coursesAdapter);

        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, DEGREES);
        degree.setAdapter(degreesAdapter);

        //university city and country list both come from CITIES and COUNTRIES string arrays
        uniCity.setAdapter(cityAdapter);
        uniLocation.setAdapter(countryAdapter);


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveProfile();
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                //use clean method for only adding a petition
                clean();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);

    }

    private void saveProfile() {
    String firstName =    fName.getText().toString();
    String lastName = lName.getText().toString();
    String birthDate = dob.getText().toString();
    String StudentGender = gender.getText().toString();
    }

    private void clean() {

    }
}