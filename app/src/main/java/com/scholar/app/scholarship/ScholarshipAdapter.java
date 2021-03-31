package com.scholar.app.scholarship;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentScholarshipListBinding;
import com.scholar.app.databinding.RvRowBinding;
import com.scholar.app.util.FirebaseUtil;

import java.util.ArrayList;

import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.Constants.SCHOLARSHIPS;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.ScholarshipViewHolder> {
    public static final String TAG = "ScholarshipAdapter";

    ArrayList<Scholarship> scholarships;
    ArrayList<Petition> petitions;

    public ScholarshipAdapter() {

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
                                    notifyItemInserted(scholarships.size() - 1);
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

        public ScholarshipViewHolder(View itemView) {
            super(itemView);
            schTitle = itemView.findViewById(R.id.scholarship_title_rvItem);
            schDescription = itemView.findViewById(R.id.scholarship_desc_rvItem);
            schAmount = itemView.findViewById(R.id.scholarship_amount_rvItem);
            schDemand = itemView.findViewById(R.id.scholarship_demand_rvItem);


            itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_ScholarshipListFragment_to_ScholarshipDetailFragment));
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

                    Log.d(TAG, "Current users born before 1900: " + value);
                    //TODO doublecheck
                    schDemand.setText(value.size());
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

        }
    }
}
