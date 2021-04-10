package com.scholar.app.scholarship;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentRequestScholarshipBinding;

import static com.scholar.app.util.Constants.DEGREES;
import static com.scholar.app.util.Constants.INITIAL_SCHOLARSHIP_STATUS;
import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.Constants.SCHOLARSHIPS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;


public class RequestScholarshipFragment extends Fragment {
    private FragmentRequestScholarshipBinding binding;
    public static final String TAG = "RequestScholarship";

    private EditText requestDescription;
    private EditText requestTitle;
    private EditText requestAmount;
    private AutoCompleteTextView requestDegree;
    private Button requestButton;

    Scholarship scholarship;
    Petition petition;

    String scholarshipId;
    String studentId;
    String petitionId;


    public RequestScholarshipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestScholarshipBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestTitle = binding.scholarshipReqTitle;
        requestDescription = binding.scholarshipReqDescription;
        requestAmount = binding.scholarshipReqAmount;
        requestDegree = binding.actvScholarshipDegree;
        ArrayAdapter<String> degreesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, DEGREES);
        requestDegree.setAdapter(degreesAdapter);
        requestButton = binding.scholarshipReqButton;


        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRequest();
                createPetition();
                //navigate to scholarshipListFragment
                NavHostFragment.findNavController(RequestScholarshipFragment.this)
                        .navigate(R.id.action_requestScholarshipFragment_to_ScholarshipListFragment);
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void createRequest() {

        String scholarshipTitle = requestTitle.getText().toString();
        String scholarshipDesc = requestDescription.getText().toString();
        String scholarshipAmount = requestAmount.getText().toString();
        String degreeType = requestDegree.getText().toString();
        String scholarshipStatus = INITIAL_SCHOLARSHIP_STATUS;
        studentId = currentUser.getUid();
        //create new scholarship
        scholarship = new Scholarship(scholarshipTitle, scholarshipDesc, degreeType, scholarshipAmount,
                scholarshipStatus, studentId);
        //create empty scholarship document then get the document id
        scholarshipId = firestoreDb.collection(SCHOLARSHIPS).document().getId();

        //fill created document with data from scholarship object
        firestoreDb.collection(SCHOLARSHIPS)
                .document(scholarshipId)
                .set(scholarship)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        firestoreDb.collection(SCHOLARSHIPS)
                .document(scholarshipId)
                .update("scholarshipId", scholarshipId);

    }

    private void createPetition() {
//create new petition
        petition = new Petition(scholarshipId, studentId);

        //create new empty petition document and get its id
        petitionId = firestoreDb.collection(PETITIONS).document().getId();

        firestoreDb.collection(PETITIONS)
                .document(petitionId)
                .update("petitionId", petitionId);

        firestoreDb.collection(PETITIONS)
                .document(petitionId)
                .set(petition)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: pettion created");
                    }
                });
        //TODO create fancy success animation
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}