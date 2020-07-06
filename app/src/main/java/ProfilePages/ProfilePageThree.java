package ProfilePages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.stamford.stamfordbloodbank.HomePages;
import com.stamford.stamfordbloodbank.R;

import java.util.HashMap;
import java.util.Map;

import javax.sql.StatementEvent;

import ProfileBottomSheed.AgeBottomSheed;
import ProfileBottomSheed.BloodBottomSheed;
import ProfileBottomSheed.HealthBootmSheed;
import ProfileBottomSheed.HeigthBottomSheed;
import ProfileBottomSheed.WeightBottomSheed;


public class ProfilePageThree extends Fragment {

    private RelativeLayout malebutton, femalebutton;
    private RelativeLayout continuebuton;
    private RelativeLayout healthbutton;

    private MaterialTextView healthfirst, healthmid, healthlast;
    private DatabaseReference SaveData;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private RelativeLayout ageButton;

    private RelativeLayout bloodbutton;
    private RelativeLayout wigthbutton;
    private RelativeLayout heigth;

    /// age
    protected MaterialTextView agefirst, agemid, agelast;
    /// age

    /// blood
    private MaterialTextView bloodfirst, bloodmid, bloodlast;
    /// blood

    ///width
    private MaterialTextView weigthfirst, weigthmid, weightlast;
    ///width

    ///heigth
    private MaterialTextView heigthfirst, heigthmid, heigthlast;
    ///heigth

    /// User info
    private String Usernametext, bloodgrouptext, Agetext, Weighttext, Campustext;
    private String DepertmentText, GenderText, HealthText, HeightText, Studentof_yestText, ImageuriText;
    /// User info

    private DatabaseReference Muser_database;

    public ProfilePageThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_page_three, container, false);

        /// heigth
        heigthfirst = view.findViewById(R.id.HeightFirst);
        heigthmid = view.findViewById(R.id.HeightMid);
        heigthlast = view.findViewById(R.id.HeightLast);
        /// heigth

        /// weight
        weigthfirst = view.findViewById(R.id.WeightFirst);
        weigthmid = view.findViewById(R.id.WeightMid);
        weightlast = view.findViewById(R.id.WeigthLast);
        /// weight

        heigth = view.findViewById(R.id.HeightButtonID);
        heigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeigthBottomSheed healthBootmSheed = new HeigthBottomSheed();
                healthBootmSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        wigthbutton = view.findViewById(R.id.WeightButttonID);
        wigthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightBottomSheed weightBottomSheed = new WeightBottomSheed();
                weightBottomSheed.show(getActivity().getSupportFragmentManager(), "open");

            }
        });

        bloodbutton = view.findViewById(R.id.FatButtonID);
        /// age
        agefirst = view.findViewById(R.id.AgeFirstText);
        agemid = view.findViewById(R.id.AgeMid);
        agelast = view.findViewById(R.id.AgeLastText);
        /// age

        //blood
        bloodfirst = view.findViewById(R.id.BloodFirstText);
        bloodmid = view.findViewById(R.id.BloodMidTex);
        bloodlast = view.findViewById(R.id.BloodLastText);
        //blood

        ///blood
        bloodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodBottomSheed bloodBottomSheed = new BloodBottomSheed();
                bloodBottomSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });
        ///blood

        ageButton = view.findViewById(R.id.AgeButtonID);

        ageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgeBottomSheed ageBottomSheed = new AgeBottomSheed();
                ageBottomSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        SaveData = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        /// health
        healthfirst = view.findViewById(R.id.HealthFirst);
        healthlast = view.findViewById(R.id.HealthLast);
        healthmid = view.findViewById(R.id.HealthMid);
        /// health

        healthbutton = view.findViewById(R.id.LevelButtonID);

        healthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthBootmSheed healthBootmSheed = new HealthBootmSheed();
                healthBootmSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        continuebuton = view.findViewById(R.id.SetupButtonID);


        malebutton = view.findViewById(R.id.MaleButtonIDBox);
        femalebutton = view.findViewById(R.id.FemaleButtonBox);
        malebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                malebutton.setBackgroundResource(R.drawable.gender_box_clikc);
                femalebutton.setBackgroundResource(R.drawable.gender_box);
                SaveData.child(CurrentUserID).child("gender").setValue("Male");


            }
        });

        femalebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femalebutton.setBackgroundResource(R.drawable.gender_box_clikc);
                malebutton.setBackgroundResource(R.drawable.gender_box);
                SaveData.child(CurrentUserID).child("gender").setValue("Female");
            }
        });

        SaveData.child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("health")) {
                                String healthget = dataSnapshot.child("health").getValue().toString();
                                if (!healthget.isEmpty()) {
                                    healthfirst.setVisibility(View.VISIBLE);
                                    healthmid.setVisibility(View.INVISIBLE);
                                    healthlast.setText(healthget);
                                    healthlast.setVisibility(View.VISIBLE);
                                }
                            } else {
                                healthfirst.setVisibility(View.INVISIBLE);
                                healthmid.setVisibility(View.VISIBLE);
                                healthlast.setVisibility(View.INVISIBLE);
                            }
                            if (dataSnapshot.hasChild("gender")) {
                                String genderget = dataSnapshot.child("gender").getValue().toString();

                                if (genderget.equals("Male")) {
                                    malebutton.setBackgroundResource(R.drawable.gender_box_clikc);
                                    femalebutton.setBackgroundResource(R.drawable.gender_box);
                                }
                                if (genderget.equals("Female")) {
                                    femalebutton.setBackgroundResource(R.drawable.gender_box_clikc);
                                    malebutton.setBackgroundResource(R.drawable.gender_box);
                                }
                            }

                            if (dataSnapshot.hasChild("Age")) {
                                String Ageget = dataSnapshot.child("Age").getValue().toString();
                                agefirst.setVisibility(View.VISIBLE);
                                agemid.setVisibility(View.INVISIBLE);
                                agelast.setVisibility(View.VISIBLE);
                                agelast.setText(Ageget);
                            } else {
                                agefirst.setVisibility(View.VISIBLE);
                                agemid.setVisibility(View.INVISIBLE);
                                agelast.setVisibility(View.VISIBLE);
                                
                            }

                            if (dataSnapshot.hasChild("Bloodgroup")) {
                                String Bloodgroupget = dataSnapshot.child("Bloodgroup").getValue().toString();
                                bloodfirst.setVisibility(View.VISIBLE);
                                bloodmid.setVisibility(View.INVISIBLE);
                                bloodlast.setVisibility(View.VISIBLE);
                                bloodlast.setText(Bloodgroupget);
                            } else {
                                bloodfirst.setVisibility(View.INVISIBLE);
                                bloodmid.setVisibility(View.VISIBLE);
                                bloodlast.setVisibility(View.INVISIBLE);

                            }

                            if (dataSnapshot.hasChild("Weight")) {

                                String Weightget = dataSnapshot.child("Weight").getValue().toString();
                                weigthfirst.setVisibility(View.VISIBLE);
                                weigthmid.setVisibility(View.INVISIBLE);
                                weightlast.setVisibility(View.VISIBLE);
                                weightlast.setText(Weightget);

                            } else {
                                weigthfirst.setVisibility(View.INVISIBLE);
                                weigthmid.setVisibility(View.VISIBLE);
                                weightlast.setVisibility(View.INVISIBLE);

                            }
                            if (dataSnapshot.hasChild("heigth")) {
                                String heigthget = dataSnapshot.child("heigth").getValue().toString();
                                heigthfirst.setVisibility(View.VISIBLE);
                                heigthmid.setVisibility(View.INVISIBLE);
                                heigthlast.setVisibility(View.VISIBLE);
                                heigthlast.setText(heigthget);
                            } else {
                                heigthfirst.setVisibility(View.INVISIBLE);
                                heigthmid.setVisibility(View.VISIBLE);
                                heigthlast.setVisibility(View.INVISIBLE);

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        save_data();

        Muser_database = FirebaseDatabase.getInstance().getReference().child("Users");
        return view;
    }

    private void save_data() {

        SaveData.child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.hasChild("Age") && dataSnapshot.hasChild("Bloodgroup") && dataSnapshot.hasChild("UserName") && dataSnapshot.hasChild("Weight") && dataSnapshot.hasChild("campus") && dataSnapshot.hasChild("depertment") && dataSnapshot.hasChild("gender") && dataSnapshot.hasChild("health") && dataSnapshot.hasChild("heigth") && dataSnapshot.hasChild("year_of_student")) {

                                if(dataSnapshot.hasChild("Age")){
                                    Agetext = dataSnapshot.child("Age").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("Bloodgroup")){
                                    bloodgrouptext = dataSnapshot.child("Bloodgroup").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("UserName")){
                                    Usernametext = dataSnapshot.child("UserName").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("Weight")){
                                    Weighttext = dataSnapshot.child("Weight").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("campus")){
                                    Campustext = dataSnapshot.child("campus").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("depertment")){
                                    DepertmentText = dataSnapshot.child("depertment").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("gender")){
                                    GenderText = dataSnapshot.child("gender").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("health")){
                                    HealthText = dataSnapshot.child("health").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("heigth")){
                                    HeightText = dataSnapshot.child("heigth").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("year_of_student")){
                                    Studentof_yestText = dataSnapshot.child("year_of_student").getValue().toString();
                                }
                                if(dataSnapshot.hasChild("profile_image_uri")){
                                    ImageuriText = dataSnapshot.child("profile_image_uri").getValue().toString();
                                }



                                continuebuton.setEnabled(true);
                                continuebuton.setBackgroundResource(R.drawable.prifilebuton_visiable);

                                continuebuton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        goto_home_page();
                                    }
                                });
                            } else {
                                continuebuton.setEnabled(false);
                                continuebuton.setBackgroundResource(R.drawable.profile_button_invisiabe);
                                continuebuton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getContext(), "Please complected profile first", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void goto_home_page() {
        Map<String, Object> usermap = new HashMap<String, Object>();
        usermap.put("Username", Usernametext);
        usermap.put("Age", Agetext);
        usermap.put("BloodGroup", bloodgrouptext);
        usermap.put("Weight", Weighttext);
        usermap.put("Campus", Campustext);
        usermap.put("Depertment", DepertmentText);
        usermap.put("Gender", GenderText);
        usermap.put("Health", HealthText);
        usermap.put("Height", HeightText);
        usermap.put("Imageuri", ImageuriText);
        usermap.put("Year_Of_Student", Studentof_yestText);


        Muser_database.child(CurrentUserID)
                .updateChildren(usermap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            SaveData.removeValue();
                            Intent intent = new Intent(getActivity(), HomePages.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}