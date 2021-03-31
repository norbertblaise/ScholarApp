
package com.scholar.app.student;

import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentEditProfileBinding;

import static com.scholar.app.util.Constants.CITIES;
import static com.scholar.app.util.Constants.COUNTRIES;
import static com.scholar.app.util.Constants.COURSES;
import static com.scholar.app.util.Constants.DEGREES;
import static com.scholar.app.util.Constants.GENDER;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    private FragmentEditProfileBinding binding;


    AutoCompleteTextView country;
    AutoCompleteTextView city;
    AutoCompleteTextView actvGender;
    AutoCompleteTextView course;
    AutoCompleteTextView actvDegree;
    AutoCompleteTextView actvUniCity;
    AutoCompleteTextView uniLocation;
    AutoCompleteTextView cResidence;
    EditText fName;
    EditText lName;
    EditText dob;
    EditText universityName;
    EditText editTextStartDate;
    EditText editTextExpectedGrad;
    EditText bioEditText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        //initializse firestore

        country = binding.actvNationality;
        cResidence = binding.actvCountryOfResidence;
        city = binding.actvCity;
        actvGender = binding.actvGender;
        course = binding.actvCourse;
        actvDegree = binding.actvDegreeType;
        uniLocation = binding.actvUniLocation;
        fName = binding.editTextFName;
        lName = binding.editTextLName;
        dob = binding.editTextDob;
        universityName = binding.editTextUniveristy;
        editTextStartDate = binding.editTextStartDate;
        editTextExpectedGrad = binding.editTextDateExpectedGrad;
        bioEditText = binding.bioEditText;


        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, CITIES);
        city.setAdapter(cityAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, COUNTRIES);
        country.setAdapter(countryAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, GENDER);
        actvGender.setAdapter(genderAdapter);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, COURSES);
        course.setAdapter(coursesAdapter);

        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, DEGREES);
        actvDegree.setAdapter(degreesAdapter);

        //university city and country list both come from CITIES and COUNTRIES string arrays
        uniLocation.setAdapter(countryAdapter);
        cResidence.setAdapter(countryAdapter);


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.me_too_button).setOnClickListener(new View.OnClickListener() {
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
                NavHostFragment.findNavController(EditProfileFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
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
        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        String name = firstName + lastName;
        String birthDate = dob.getText().toString();
        String bio = bioEditText.getText().toString();
        String studentCountry = country.getText().toString();
        String studentCity = city.getText().toString();
        String countryOfResidence = cResidence.getText().toString();
        String gender = actvGender.getText().toString();
        String university = universityName.getText().toString();
        String courseOfStudy = course.getText().toString();
        String degree = actvDegree.getText().toString();
        String uniCountry = uniLocation.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String gradDate = editTextExpectedGrad.getText().toString();

        Student student = new Student(name, birthDate, gender, bio, studentCountry, countryOfResidence, studentCity,
                university, uniCountry, courseOfStudy, degree, startDate, gradDate);

        firestoreDb.collection("students")
                .add(student)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        student.setStudentProfileId(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    private void clean() {

    }
}