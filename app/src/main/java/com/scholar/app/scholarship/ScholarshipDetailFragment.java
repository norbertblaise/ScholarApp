package com.scholar.app.scholarship;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentScholarshipDetailBinding;

import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class ScholarshipDetailFragment extends Fragment {
    private FragmentScholarshipDetailBinding binding;
    public static final String TAG = "ScholarshipDetail";
    Scholarship selectedScholarship;
    Petition petition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
//            Scholarship scholarship = getArguments().getSerializable(SELECTED_SCHOLARSHIP);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentScholarshipDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            ScholarshipDetailFragmentArgs args = ScholarshipDetailFragmentArgs.fromBundle(bundle);
            selectedScholarship = args.getSelectedScholarship();

            TextView scholarshipTitle = binding.scholarshipDetTitleTextView;
            TextView scholarshipDescription = binding.scholarshipDescTextView;
            TextView scholarshipAmount = binding.scholarshipDetAmountTextView;

            scholarshipTitle.setText(selectedScholarship.getScholarshipTitle());
            scholarshipDescription.setText(selectedScholarship.getDescription());
            scholarshipAmount.setText(selectedScholarship.getAmountOffered());
        }else{
            Log.d(TAG, "onViewCreated: bundle null");
        }

        MaterialButton meToo = binding.meTooButton;
        //check if studenthas already pettioned
        firestoreDb.collection(PETITIONS)
                .whereEqualTo("StudentId", currentUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            meToo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //create a new petition document
                                    createPetition(selectedScholarship.getScholarshipId(), currentUser.getUid());
                                    //TODO show fancy onsuccess animation
                                    NavHostFragment.findNavController(ScholarshipDetailFragment.this)
                                            .navigate(R.id.action_ScholarshipDetailFragment_to_ScholarshipListFragment);

                                }
                            });
                        }else{
                            //set the text to petitioned and make it green
                            meToo.setText(R.string.petitioned);
                            meToo.setTextColor(getResources().getColor(R.color.white));
                            meToo.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                            meToo.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }
                });

    }

    public void createPetition(String scholarshipId, String studentId){
        petition = new Petition(scholarshipId,studentId);
        firestoreDb.collection(PETITIONS)
                .add(petition)
                .addOnSuccessListener(documentReference -> {
                    //TODO add fancy success animation
                    Toast.makeText(getContext(), "You have successfully signed", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Log.d(TAG, "onFailure: doc not added"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}