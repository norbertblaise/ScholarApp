package com.scholar.app.scholarship;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.scholar.app.R;
import com.scholar.app.student.StudentProfileActivity;
import com.scholar.app.util.FirebaseUtil;

import static com.scholar.app.scholarship.ScholarshipListFragment.TAG;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }else if (id == R.id.logout_action){
//            AuthUI.getInstance()
//                    .signOut(this)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d(TAG, "onComplete: User logged out");
//                            FirebaseUtil.attachListener();
//                        }
//                    });
//            FirebaseUtil.detachListener();
//        }
        switch(item.getItemId()){
            case R.id.action_view_profile:
                Intent intent = new Intent(this, StudentProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.dark_mode_switch:
                //TODO toggle darkmode
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}