package com.stamford.stamfordbloodbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainFragment extends AppCompatActivity {

    private RelativeLayout phonebutton;
    private FrameLayout frameLayout;
    private Fragment phonefregement;
    private RelativeLayout gurstlogin;
    private FirebaseAuth Mauth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        Mauth = FirebaseAuth.getInstance();
        phonebutton = findViewById(R.id.ContinuePhoneButtonID);
        gurstlogin = findViewById(R.id.ContinueGuestButtonID);



        phonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   goto_register_layout(new PhoneLogin());

                Intent intent = new Intent(getApplicationContext(), PhoneLogin.class);
                startActivity(intent);
            }
        });

    }


  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }
*/
  /*  private void goto_register_layout(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);

        fragmentTransaction.replace(R.id.MainFramLayoutID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }*/



    /*private void goto_homepage(Fragment fragment){

        Bundle bundle = new Bundle();
        bundle.putString("KEY", "sajib roy");
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);

        fragmentTransaction.replace(R.id.MainFramLayoutID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            Intent intent = new Intent(getApplicationContext(), HomeContainer.class);
            startActivity(intent);
        }

    }

   /* private void goto_home(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.GetStartRootID, fragment);
        fragmentTransaction.commit();
    }*/
}