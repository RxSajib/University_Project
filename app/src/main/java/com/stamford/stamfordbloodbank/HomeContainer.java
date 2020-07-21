package com.stamford.stamfordbloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class HomeContainer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_container);


       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
*/
        Setup_Layout(new HomePages());
    }

    private void Setup_Layout(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomeContinerID, fragment);
        fragmentTransaction.commit();
    }
}