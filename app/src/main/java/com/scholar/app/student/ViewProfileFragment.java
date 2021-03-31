package com.scholar.app.student;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.scholar.app.R;
import com.scholar.app.databinding.FragmentViewProfileBinding;
import com.scholar.app.util.FirebaseUtil;

import static com.scholar.app.util.FirebaseUtil.firestoreDb;

public class ViewProfileFragment extends Fragment {


    private FragmentViewProfileBinding binding;
    private static final String TAG = "ViewProfileFragment";




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = FragmentViewProfileBinding.inflate(inflater, container, false);
        View view  = binding.getRoot();

        firestoreDb.collection("students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        binding.editProfileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ViewProfileFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}