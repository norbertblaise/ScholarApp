
package com.scholar.app.student;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentEditProfileBinding;

import java.util.Calendar;

import static com.scholar.app.util.Constants.CITIES;
import static com.scholar.app.util.Constants.COUNTRIES;
import static com.scholar.app.util.Constants.COURSES;
import static com.scholar.app.util.Constants.DEGREES;
import static com.scholar.app.util.Constants.GENDER;
import static com.scholar.app.util.Constants.STUDENTS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    private FragmentEditProfileBinding binding;
    Student student;


    AutoCompleteTextView country;
    AutoCompleteTextView city;
    AutoCompleteTextView actvGender;
    AutoCompleteTextView course;
    AutoCompleteTextView actvDegree;
    AutoCompleteTextView actvUniCity;
    AutoCompleteTextView uniLocation;
    EditText fName;
    EditText fullName;
    EditText dob;
    EditText universityName;
    EditText editTextStartDate;
    EditText editTextExpectedGrad;
    EditText bioEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();



        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize views
        country = binding.actvCountry;
        city = binding.actvCity;
        actvGender = binding.actvGender;
        course = binding.actvCourse;
        actvDegree = binding.actvDegreeType;
        uniLocation = binding.actvUniLocation;
        fullName = binding.editTextFullName;
        dob = binding.editTextDob;
        universityName = binding.editTextUniveristy;
        editTextStartDate = binding.editTextStartDate;
        editTextExpectedGrad = binding.editTextDateExpectedGrad;
        bioEditText = binding.bioEditText;

        //add text watchers to date fields
        //dob textwatcher
        dob.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dob.setText(current);
                    dob.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //startdate textwatcher
        editTextStartDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextStartDate.setText(current);
                    editTextStartDate.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //graddate textwatcher
        editTextExpectedGrad.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editTextExpectedGrad.setText(current);
                    editTextExpectedGrad.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        Bundle bundle = getArguments();
        if (bundle != null){
            EditProfileFragmentArgs args = EditProfileFragmentArgs.fromBundle(bundle);
            student = args.getStudent();

            //set the text with current student profile values
            //check if displayName exists
            if (currentUser.getDisplayName() != null && student != null){
                fullName.setText(currentUser.getDisplayName());
            }else{
             //   fullName.setText(student.getName());
            }
            try{
                country.setText(student.getCountry());
                city.setText(student.getCity());
                actvGender.setText(student.getGender());
                course.setText(student.getCourseOfStudy());
                actvDegree.setText(student.getDegree());
                uniLocation.setText(student.getUniCountry());
                dob.setText(student.getDob());
                universityName.setText(student.getUniversity());
                editTextStartDate.setText(student.getStartDate());
                editTextExpectedGrad.setText(student.getExpectedGradDate());
                bioEditText.setText(student.getBio());
            } catch(Exception e){
                Log.d(TAG, "onViewCreated: " + e);
            }
        }

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
        String studentId = currentUser.getUid();
        String name;
        //check if displayName exists
        if (currentUser.getDisplayName() != null){
            name = currentUser.getDisplayName();
        }
        name = fullName.getText().toString();
        String birthDate = dob.getText().toString();
        String bio = bioEditText.getText().toString();
        String studentCountry = country.getText().toString();
        String studentCity = city.getText().toString();
        String gender = actvGender.getText().toString();
        String university = universityName.getText().toString();
        String courseOfStudy = course.getText().toString();
        String degree = actvDegree.getText().toString();
        String uniCountry = uniLocation.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String gradDate = editTextExpectedGrad.getText().toString();

        Student student = new Student(studentId, name, birthDate, gender, bio, studentCountry, studentCity,
                university, uniCountry, courseOfStudy, degree, startDate, gradDate);

        //create  doc with studentId as its documentId
        firestoreDb.collection(STUDENTS).document(currentUser.getUid()).set(student);


        //if displayname exists, set it to the name entered
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        currentUser.updateProfile(profileUpdates);

    }

    private void clean() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}