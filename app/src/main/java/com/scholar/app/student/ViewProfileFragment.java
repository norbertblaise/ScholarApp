package com.scholar.app.student;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentViewProfileBinding;
import com.scholar.app.util.FirebaseUtil;

import java.util.ArrayList;

import static com.scholar.app.util.Constants.STUDENTS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class ViewProfileFragment extends Fragment {


    private FragmentViewProfileBinding binding;
    private static final String TAG = "ViewProfileFragment";

    Student student;
    ArrayList<Student> studentArrayList;
    TextView studentName;
    TextView studentHome;
    TextView studentBio;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentViewProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        studentName = binding.studentNameTextView;
        studentHome = binding.studentLocationTextView;
        studentBio = binding.studentBioTextView;

        //getstudentdetails
        DocumentReference docRef = firestoreDb.collection(STUDENTS).document(currentUser.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    //get all fields

                    String name = documentSnapshot.get("name").toString();
                    String city = documentSnapshot.get("city").toString();
                    String bio = documentSnapshot.get("bio").toString();
                    String gender = documentSnapshot.get("gender").toString();
                    String dob = documentSnapshot.get("dob").toString();
                    String country = documentSnapshot.get("country").toString();
                    String university = documentSnapshot.get("university").toString();
                    String uniCountry = documentSnapshot.get("uniCountry").toString();
                    String courseOfStudy = documentSnapshot.get("courseOfStudy").toString();
                    String degree = documentSnapshot.get("degree").toString();
                    String startDate = documentSnapshot.get("startDate").toString();
                    String expectedGradDate = documentSnapshot.get("expectedGradDate").toString();
                    String studentLocation = city + ", " + country;

                    //create a student object
                    student = new Student(currentUser.getUid(), name, dob, gender, bio, country, city, university, uniCountry,
                            courseOfStudy, degree, startDate, expectedGradDate);

                    //Populate Profile fragment textviews
                    studentName.setText(name);
                    studentHome.setText(studentLocation);
                    studentBio.setText(bio);
                }
            }
        });


        binding.editProfileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass student object to EditProfileFragment


                NavHostFragment.findNavController(ViewProfileFragment.this)
                        .navigate(ViewProfileFragmentDirections.actionStudentProfileFragmentToEditProfileFragment(student));
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}