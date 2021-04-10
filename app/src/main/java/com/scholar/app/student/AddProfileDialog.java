package com.scholar.app.student;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.scholar.app.R;

public class AddProfileDialog extends DialogFragment {
    //emptystudent to get passed the safeargs issue
    String def = "";
    Student student = new Student(def, def, def, def, def,
            def, def, def, def, def, def,
            def, def);
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("Student Profile Missing")
                .setMessage(R.string.add_profile_message)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavHostFragment.findNavController(AddProfileDialog.this)
                                .navigate(AddProfileDialogDirections.actionAddProfileDialogToEditProfileFragment(student));
                    }
               });

//        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
//
//                builder.setTitle("Student Profile Missing")
//                .setMessage("You need to complete your student profile to continue")
//                .setPositiveButton("add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        NavController.findNavController.navigate()
//                    }
//                });
        return  builder.create();
    }
}
