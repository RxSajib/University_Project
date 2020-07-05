package ProfilePages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import ProfileBottomSheed.Campus_Bottomshed;
import ProfileBottomSheed.Depertment_bottom_sheed;
import com.stamford.stamfordbloodbank.R;
import ProfileBottomSheed.YearOfStudentBottmSheed;


public class ProfileSecondPage extends Fragment implements Depertment_bottom_sheed.BottomsheedLisiner {

    private RelativeLayout depertmentbutton;
    private RelativeLayout campusbutton;
    private RelativeLayout yearofstudent;
    private RelativeLayout acadamic_button;
    private RelativeLayout yearof_student;

    /// campus text
    private MaterialTextView campusfirstname, campusmidname, campuslastname;
    /// campus text

    /// depertment text
    private MaterialTextView depertmentfirstname, depertmentmidname, depertmentlasttext;
    /// depertment text

    /// yaer of student
    private MaterialTextView yearof_studentfirst, yearof_studentmid, yearof_studentlast;
    /// yaer of student

    private DatabaseReference saveuser;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    /// subject get
    private String depertmentget,year_of_studentget, campusget;
    /// subject get

    public ProfileSecondPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.profile_second_page, container, false);
        campusbutton = view.findViewById(R.id.CampusButtonID);
        yearofstudent = view.findViewById(R.id.YearOfstudentButtonID);
        acadamic_button = view.findViewById(R.id.AccadamcInfoContinueButtonID);
        acadamic_button.setEnabled(false);

        /// year of student get
        yearof_studentfirst = view.findViewById(R.id.YesrstudentStartText);
        yearof_studentmid = view.findViewById(R.id.YearStudentMidText);
        yearof_studentlast = view.findViewById(R.id.YearstudentLastText);
        /// year of student get

        yearofstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YearOfStudentBottmSheed yearOfStudentBottmSheed = new YearOfStudentBottmSheed();
                yearOfStudentBottmSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        saveuser = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        //// campus details
        campusfirstname = view.findViewById(R.id.CampusFirstname);
        campusmidname = view.findViewById(R.id.CampusMidName);
        campuslastname = view.findViewById(R.id.CampusLastname);
        //// campus details

        /// depetmentlasttext
        depertmentfirstname = view.findViewById(R.id.DepertMentFirstText);
        depertmentmidname = view.findViewById(R.id.DepertmentMidname);
        depertmentlasttext = view.findViewById(R.id.DepertmentLast);
        /// depetmentlasttext


        depertmentbutton = view.findViewById(R.id.DepertmentButtonID);
        depertmentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Depertment_bottom_sheed depertment_bottom_sheed = new Depertment_bottom_sheed();
            depertment_bottom_sheed.show(getActivity().getSupportFragmentManager(), "opne");

            }
        });

        /*campusmidname.setVisibility(View.INVISIBLE);
        campusfirstname.setVisibility(View.VISIBLE);
        campuslastname.setVisibility(View.VISIBLE);
        campuslastname.setText("Siddheshwari");*/

        campusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Campus_Bottomshed campus_bottomshed = new Campus_Bottomshed();
              campus_bottomshed.show(getActivity().getSupportFragmentManager(), "open");

            }
        });




        saveuser.child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.hasChild("depertment")){
                                 depertmentget = dataSnapshot.child("depertment").getValue().toString();
                                if(!depertmentget.isEmpty()){
                                    depertmentfirstname.setVisibility(View.VISIBLE);
                                    depertmentmidname.setVisibility(View.INVISIBLE);
                                    depertmentlasttext.setText(depertmentget);
                                    depertmentlasttext.setVisibility(View.VISIBLE);
                                }
                                else {

                                }
                            }
                            else {
                                depertmentfirstname.setVisibility(View.INVISIBLE);
                                depertmentmidname.setVisibility(View.VISIBLE);
                                depertmentlasttext.setVisibility(View.INVISIBLE);
                            }

                            if(dataSnapshot.hasChild("year_of_student")){
                                 year_of_studentget = dataSnapshot.child("year_of_student").getValue().toString();
                                if(!year_of_studentget.isEmpty()){
                                    yearof_studentfirst.setVisibility(View.VISIBLE);
                                    yearof_studentmid.setVisibility(View.INVISIBLE);
                                    yearof_studentlast.setVisibility(View.VISIBLE);
                                    yearof_studentlast.setText(year_of_studentget);
                                }
                            }
                            else {
                                yearof_studentfirst.setVisibility(View.INVISIBLE);
                                yearof_studentmid.setVisibility(View.VISIBLE);
                                yearof_studentlast.setVisibility(View.INVISIBLE);

                            }

                            if(dataSnapshot.hasChild("campus")){
                                 campusget = dataSnapshot.child("campus").getValue().toString();
                                if(!campusget.isEmpty()){
                                    campusmidname.setVisibility(View.INVISIBLE);
                                    campusfirstname.setVisibility(View.VISIBLE);
                                    campuslastname.setVisibility(View.VISIBLE);
                                    campuslastname.setText(campusget);
                                }
                            }
                            else {
                                campusmidname.setVisibility(View.VISIBLE);
                                campusfirstname.setVisibility(View.INVISIBLE);
                                campuslastname.setVisibility(View.INVISIBLE);

                            }

                            if(dataSnapshot.hasChild("depertment") && dataSnapshot.hasChild("year_of_student") && dataSnapshot.hasChild("campus")){

                                acadamic_button.setBackgroundResource(R.drawable.prifilebuton_visiable);
                                acadamic_button.setEnabled(true);

                                acadamic_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        goto_three_page(new ProfilePageThree());
                                    }
                                });

                            }
                            else {
                                acadamic_button.setBackgroundResource(R.drawable.profile_button_invisiabe);
                                acadamic_button.setEnabled(false);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return view;
    }

    @Override
    public void onButtonClick(String mytext) {
        Toast.makeText(getContext(), mytext, Toast.LENGTH_LONG).show();
    }

    private void goto_three_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);
        fragmentTransaction.replace(R.id.ProfileRootlayoutID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}