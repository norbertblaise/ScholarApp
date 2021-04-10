package com.scholar.app.scholarship;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.util.FirebaseUtil;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;
import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.Constants.SCHOLARSHIPS;
import static com.scholar.app.util.FirebaseUtil.currentUser;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;


public class OldScholarshipAdapter extends RecyclerView.Adapter<OldScholarshipAdapter.ScholarshipViewHolder> {
    public static final String TAG = "ScholarshipAdapter";

    ArrayList<Scholarship> scholarships;
    ArrayList<Petition> petitions;
    NavController navController;


    public OldScholarshipAdapter() {

        scholarships = FirebaseUtil.mScholarships;
        FirebaseUtil.firestoreDb.collection(SCHOLARSHIPS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d("TAG", "New Msg: " + dc.getDocument().toObject(Scholarship.class));
                                    Scholarship scholarship = dc.getDocument().toObject(Scholarship.class);
                                    Log.d(TAG, "onEvent: " + scholarship.getScholarshipTitle());
                                    //TODO fill in  scholarship fields from document fields and set the text of their views

                                    scholarships.add(scholarship);
//                                    notifyItemInserted(scholarships.size() - 1);
                                    notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    Log.d("TAG", "Modified Msg: " + dc.getDocument().toObject(Scholarship.class));
                                    break;
                                case REMOVED:
                                    Log.d("TAG", "Removed Msg: " + dc.getDocument().toObject(Scholarship.class));
                                    break;
                            }
                        }

                    }
                });
    }

    @NonNull
    @Override
    public ScholarshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rv_row, parent, false);
        return new ScholarshipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarshipViewHolder holder, int position) {
        Scholarship scholarship = scholarships.get(position);
        holder.bind(scholarship);
    }

    @Override
    public int getItemCount() {
        return scholarships.size();
    }

    public class ScholarshipViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        //        RvRowBinding binding;
//
//        public ScholarshipViewHolder(RvRowBinding b) {
//
//            super(b.getRoot());
//            binding = b;
//        }
        TextView schTitle;
        TextView schDescription;
        TextView schAmount;
        TextView schDemand;
        ImageButton plusOneButton;

        public ScholarshipViewHolder(View itemView) {
            super(itemView);
            schTitle = itemView.findViewById(R.id.scholarship_title_rvItem);
            schDescription = itemView.findViewById(R.id.scholarship_desc_rvItem);
            schAmount = itemView.findViewById(R.id.scholarship_amount_rvItem);
            schDemand = itemView.findViewById(R.id.scholarship_demand_rvItem);
            plusOneButton = itemView.findViewById(R.id.scholarship_plusOne_button);

            //fixme this onclick listener might not work
            plusOneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a new petition
                    int position = getAdapterPosition();
                    Scholarship selectedScholarship = scholarships.get(position);
                    Petition petition = new Petition(selectedScholarship.getScholarshipId(), currentUser.getUid());
                    //add pettions to firestore
                    firestoreDb.collection(PETITIONS)
                            .add(petition)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "onSuccess: petition added");
                                    //todo add fancy success animation
                                }
                            });
                }
            });


//            itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_ScholarshipListFragment_to_ScholarshipDetailFragment));
            itemView.setOnClickListener(this);

        }

        public void bind(Scholarship scholarship) {
            //getting the demand for a particular scholarship
            FirebaseUtil.firestoreDb.collection(PETITIONS)
                    .whereEqualTo("scholarshipId", scholarship.getScholarshipId())
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    Log.d(TAG, "Number of Petitions: " + value);
                    //TODO doublecheck
                    schDemand.setText(String.valueOf(value.size()));
                }
            });
            schTitle.setText(scholarship.getScholarshipTitle());
            schDescription.setText(scholarship.getDescription());
            schAmount.setText(scholarship.getAmountOffered());

        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d(TAG, "onClick: " + String.valueOf(position));
            Scholarship selectedScholarship = scholarships.get(position);

            //save scholarship into safeArgs and pass to scholarshipDetailFragment
//            NavAction action = ScholarshipListFragmentDirections.actionScholarshipListFragmentToScholarshipDetailFragment(selectedScholarship);
            findNavController(v).navigate(ScholarshipListFragmentDirections.actionScholarshipListFragmentToScholarshipDetailFragment(selectedScholarship));

//            //bundle exp
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(SELECTED_SCHOLARSHIP, selectedScholarship);
//            navController.navigate(R.id.action_ScholarshipListFragment_to_ScholarshipDetailFragment, bundle);
        }
    }
}
