package com.stamford.stamfordbloodbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import BootmNavPages.BottomHomePage;
import BootmNavPages.BottomInboxPage;
import BootmNavPages.BottomNewsPage;
import BootmNavPages.BottomSearchPage;
import ProfilePages.ProfileSetActivity;
import meow.bottomnavigation.MeowBottomNavigation;


public class HomePages extends Fragment {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView menubutton;
    private FirebaseAuth Mauth;
    private String CurrentuerID;
    private DatabaseReference Muser_database;

    private BottomNavigationView bottomNavigationView;


    public HomePages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_pages, container, false);

        open_default_page(new BottomHomePage());

        bottomNavigationView = view.findViewById(R.id.BottomNavID);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.HomeID){
                    home_pages(new BottomHomePage());
                }

                if(item.getItemId() == R.id.SearchID){
                    search_page(new BottomSearchPage());
                }

                if(item.getItemId() == R.id.InboxID){
                    index_page(new BottomInboxPage());
                }

                if(item.getItemId() == R.id.NewsID){
                    news_page(new BottomNewsPage());
                }

                if(item.getItemId() == R.id.NotifactionID){
                    notifaction_page(new BottomNewsPage());
                }

                return true;
            }
        });

        Muser_database = FirebaseDatabase.getInstance().getReference().child("Users");
        Mauth = FirebaseAuth.getInstance();
        CurrentuerID = Mauth.getCurrentUser().getUid();



        navigationView = view.findViewById(R.id.NavagationViewID);
        drawerLayout = view.findViewById(R.id.DrawerLayoutID);
        menubutton = view.findViewById(R.id.MenuButtonID);

        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setItemIconTintList(null);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.dashboardID) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    drawerLayout.setClickable(true);
                    drawerLayout.setClickable(true);

                    //     goto_dashbord(new Dashboard());
                }

                return true;
            }
        });


        return view;
    }



    private void home_pages(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

    private void notifaction_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

    private void news_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

    private void  index_page (Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

    private void  search_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

    private void open_default_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomePageFreamID, fragment);
        fragmentTransaction.commit();
    }

/*    private void goto_dashbord(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);

        fragmentTransaction.replace(R.id.HomeContainer, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }*/


    /*@Override
    public void onStart() {

        FirebaseUser Muser = Mauth.getCurrentUser();
        if (Muser == null) {
            /// goto login page
        } else {
            Muser_database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(CurrentuerID)) {
                        Intent intent = new Intent(getActivity(), ProfileSetActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        super.onStart();
    }*/

   /* private void goto_profile_pages(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);
        fragmentTransaction.replace(R.id.HomeContainer, fragment);
        fragmentTransaction.commit();
    }*/


}