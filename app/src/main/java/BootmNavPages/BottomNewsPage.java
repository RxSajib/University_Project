package BootmNavPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stamford.stamfordbloodbank.R;

import java.util.Calendar;

import All_PogoClass.Requestgetset;
import ChatPage.ChatPages;
import DonatePage.DonarPage;
import PostPages.PostContiner;
import de.hdodenhof.circleimageview.CircleImageView;


public class BottomNewsPage extends Fragment {

    private ExtendedFloatingActionButton postbutton;
    private CircleImageView profileimage;
    private RecyclerView list;
    private DatabaseReference Mpost_database;
    private FirebaseAuth Mauth;
    private String CurrentuserID;

    private LottieAnimationView searching, nopost;

    private MaterialTextView day_status;

    private DatabaseReference MuserDatabase;
    private CircleImageView profileimageview;
    private MaterialTextView username;


    public BottomNewsPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_news_page, container, false);

        username = view.findViewById(R.id.UserNameView);
        profileimageview = view.findViewById(R.id.ProfileImageViewID);
        searching = view.findViewById(R.id.SearchingButtonID);
        nopost = view.findViewById(R.id.NodataButtonID);

        day_status = view.findViewById(R.id.DayStatusTextID);
        searching.setVisibility(View.VISIBLE);
        nopost.setVisibility(View.GONE);

        Mauth = FirebaseAuth.getInstance();
        CurrentuserID = Mauth.getCurrentUser().getUid();

        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        Mpost_database = FirebaseDatabase.getInstance().getReference().child("BloodRequest");
        list = view.findViewById(R.id.RequestListViewID);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setHasFixedSize(true);


        profileimage = view.findViewById(R.id.ProfileImageViewID);
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        postbutton = view.findViewById(R.id.PostButtonID);

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostContiner.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        Mpost_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    searching.setVisibility(View.GONE);
                    nopost.setVisibility(View.GONE);
                }
                else {
                    searching.setVisibility(View.GONE);
                    nopost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 6){
       
            day_status.setText("Good Night");
        }else if(timeOfDay >= 6 && timeOfDay < 12){
            day_status.setText("Good Morning");

        }else if(timeOfDay >= 12 && timeOfDay < 18){
            day_status.setText("Good AfterNoon");

        }else if(timeOfDay >= 18 && timeOfDay < 24){
            day_status.setText("Good Evening");

        }


        get_profiledata();

        return view;
    }

    private void goto_full_details(Fragment fragment, String UID){

        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        bundle.putString("UID", UID);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.HomeContinerID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onStart() {

        FirebaseRecyclerAdapter<Requestgetset, RequestListHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Requestgetset, RequestListHolder>(
                Requestgetset.class,
                R.layout.user_post_design,
                RequestListHolder.class,
                Mpost_database

        ) {
            @Override
            protected void populateViewHolder(final RequestListHolder requestListHolder, final Requestgetset requestgetset, int i) {
                final String UID = getRef(i).getKey();
                Mpost_database.child(UID)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    searching.setVisibility(View.GONE);
                                    nopost.setVisibility(View.GONE);

                                    if(dataSnapshot.hasChild("bloodgroup")){
                                        String bloodgroup = dataSnapshot.child("bloodgroup").getValue().toString();
                                        if(bloodgroup.equals("A+")){
                                            requestListHolder.aplus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("A-")){
                                            requestListHolder.aminus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("B+")){
                                            requestListHolder.bplus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("B-")){
                                            requestListHolder.bminus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("O+")){
                                            requestListHolder.oplus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("O-")){
                                            requestListHolder.ominus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("AB+")){
                                            requestListHolder.abplus.setBackgroundResource(R.drawable.blood_selected);
                                        }

                                        if(bloodgroup.equals("AB-")){
                                            requestListHolder.abminus.setBackgroundResource(R.drawable.blood_selected);
                                        }
                                    }
                                    else {

                                    }


                                    if(dataSnapshot.hasChild("hospital_name")){
                                        String hospital_name = dataSnapshot.child("hospital_name").getValue().toString();
                                        requestListHolder.setPasent_hospitalset(hospital_name);
                                    }
                                    else {

                                    }


                                    if(dataSnapshot.hasChild("location")){
                                        String location = dataSnapshot.child("location").getValue().toString();
                                        requestListHolder.setLocationset(location);
                                    }
                                    else {

                                    }


                                    if(dataSnapshot.hasChild("pasent_name")){
                                        String pasentname = dataSnapshot.child("pasent_name").getValue().toString();
                                        requestListHolder.setPasent_nameset(pasentname);
                                    }
                                    else {

                                    }


                                    if(dataSnapshot.hasChild("pasent_number")){
                                        String pasent_number = dataSnapshot.child("pasent_number").getValue().toString();

                                    }
                                    else {

                                    }


                                    if(dataSnapshot.hasChild("unitof_blood")){
                                        String unitblood = dataSnapshot.child("unitof_blood").getValue().toString();
                                        requestListHolder.setPasent_blood_unitset(unitblood);
                                    }
                                    else {

                                    }

                                    requestListHolder.donate_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            goto_full_details(new DonarPage(),UID);
                                        }
                                    });

                                    requestListHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    });
/*
                                    if(dataSnapshot.hasChild("UID")){
                                        String postuid = dataSnapshot.child("UID").getValue().toString();

                                        requestListHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                if(postuid.equals(CurrentuserID)){
                                                    Toast.makeText(getActivity(), "myuid", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    /// hear uid get another page
                                                    goto_chat(new ChatPages(), postuid);
                                                }
                                            }
                                        });


                                    }*/




                                            if(dataSnapshot.hasChild("UID")){
                                               final String postuid = dataSnapshot.child("UID").getValue().toString();

                                                if(postuid.equals(CurrentuserID)){
                                                    Toast.makeText(getActivity(), "myuid", Toast.LENGTH_LONG).show();
                                                }
                                                else {

                                                    requestListHolder.Mview.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            goto_chat(new ChatPages(), postuid);

                                                        }

                                                        });
                                                }

                                        }


                                }
                                else {
                                    searching.setVisibility(View.GONE);
                                    nopost.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        };


        list.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class RequestListHolder extends RecyclerView.ViewHolder{

        private Context context;
        private View Mview;
        private MaterialTextView pasent_name;
        private MaterialTextView pasent_hospital;
        private MaterialTextView pasent_blood_unit;
        private MaterialTextView location;

        private RelativeLayout donate_button;


        private RelativeLayout aplus, aminus, bplus, bminus, oplus, ominus, abplus, abminus;

        public RequestListHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            context = Mview.getContext();

            pasent_name = Mview.findViewById(R.id.PasentnameText);
            pasent_hospital = Mview.findViewById(R.id.PPHospitalText);
            pasent_blood_unit = Mview.findViewById(R.id.UnitOfBlood);
            location = Mview.findViewById(R.id.PasentLocation);

            aplus = Mview.findViewById(R.id.AplusContiner);
            aminus = Mview.findViewById(R.id.AminusContiner);
            bplus = Mview.findViewById(R.id.BplusContiner);
            bminus = Mview.findViewById(R.id.BminusContiner);
            oplus = Mview.findViewById(R.id.OplusContinerID);
            ominus = Mview.findViewById(R.id.OminusContiner);
            abplus = Mview.findViewById(R.id.ABplusContiner);
            abminus = Mview.findViewById(R.id.AbminusContiner);

            donate_button = Mview.findViewById(R.id.DonateButtonID);
        }

        public void setPasent_nameset(String nam){
            pasent_name.setText(nam);
        }

        public void setPasent_hospitalset(String hosp){
            pasent_hospital.setText(hosp);
        }

        public void setPasent_blood_unitset(String unit){
            pasent_blood_unit.setText(unit);
        }

        public void setLocationset(String loc){
            location.setText(loc);
        }
    }

    private void get_profiledata(){
        MuserDatabase.child(CurrentuserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                           if(dataSnapshot.hasChild("Imageuri")){
                               String uri = dataSnapshot.child("Imageuri").getValue().toString();
                               Picasso.with(getActivity()).load(uri).placeholder(R.drawable.person_design).into(profileimageview);
                           }
                           if(dataSnapshot.hasChild("Username")){

                               String nameget = dataSnapshot.child("Username").getValue().toString();
                               username.setText(nameget);

                               if(dataSnapshot.hasChild("Gender")){
                                   String sex = dataSnapshot.child("Gender").getValue().toString();

                                   if(sex.equals("Female")){
                                       username.setText("Mis "+nameget);
                                   }
                                   if(sex.equals("Male")){
                                       username.setText("Mr "+nameget);
                                   }
                               }
                           }
                        }
                        else {
                            Toast.makeText(getContext(), "No users found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void goto_chat(Fragment fragment, String UID){

        Bundle bundle = new Bundle();
        bundle.putString("UID", UID);
        fragment.setArguments(bundle);
  //      Toast.makeText(getActivity(), UID, Toast.LENGTH_LONG).show();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();


        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);
        fragmentTransaction.replace(R.id.HomeContinerID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}