package com.scholar.app.scholarship;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;

import static com.scholar.app.util.Constants.PETITIONS;
import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class ScholarshipFirebaseUiAdapter
        extends FirestoreRecyclerAdapter<Scholarship, ScholarshipFirebaseUiAdapter.ScholarshipHolder> {
    private OnItemClickListener listener;
    public static final String TAG = "Scholarship Adapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ScholarshipFirebaseUiAdapter(@NonNull FirestoreRecyclerOptions<Scholarship> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ScholarshipHolder holder, int position, @NonNull Scholarship model) {
        holder.schTitle.setText(model.getScholarshipTitle());
        holder.schDescription.setText(model.getDescription());
        holder.schAmount.setText(model.getAmountOffered());

        //get demand for each scholarship

        firestoreDb.collection(PETITIONS)
                .whereEqualTo("scholarshipId", model.getScholarshipId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       if (value != null) {
                           holder.schDemand.setText(String.valueOf(value.size()));
                       }else {
                           Log.d(TAG, "onEvent: " + error.toString());
                       }
                    }
                });
    }

    @NonNull
    @Override
    public ScholarshipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row, parent, false);
        return new ScholarshipHolder(view);
    }

    class ScholarshipHolder extends RecyclerView.ViewHolder {
        TextView schTitle;
        TextView schDescription;
        TextView schAmount;
        TextView schDemand;
        ImageButton plusOneButton;

        public ScholarshipHolder(@NonNull View itemView) {
            super(itemView);
            schTitle = itemView.findViewById(R.id.scholarship_title_rvItem);
            schDescription = itemView.findViewById(R.id.scholarship_desc_rvItem);
            schAmount = itemView.findViewById(R.id.scholarship_amount_rvItem);
            schDemand = itemView.findViewById(R.id.scholarship_demand_rvItem);
            plusOneButton = itemView.findViewById(R.id.scholarship_plusOne_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: scholarship card clicked");
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            plusOneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: plus one clicked");
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onPlusOneClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
        void onPlusOneClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
