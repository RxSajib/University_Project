package MyProfile;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stamford.stamfordbloodbank.R;


public class MyProfiele extends Fragment {


    private String depetment[] = {"Business Administration", "Pharmacy", "Micro Biology", "Environmental Science", "English", "Economics", "Film and Media Studies", "Journalism and Media Studies",
            "Public Administration", "Law", "Computer Science", "Electrical & Electronic Engineering", "Civil Engineering", "Architecture"};


    private RelativeLayout depertmentbutton;

    private DatabaseReference MuserDatavase;
    private String CurrentUserID;
    private FirebaseAuth Mauth;

    private EditText username;
    private MaterialTextView depttext;
    private ImageView male, female;

    public MyProfiele() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_profiele, container, false);

        male = view.findViewById(R.id.MaleImage);
        female = view.findViewById(R.id.Femaleicon);
        depttext  = view.findViewById(R.id.Depttext);
        username = view.findViewById(R.id.UserProfileNameID);

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        MuserDatavase = FirebaseDatabase.getInstance().getReference().child("Users");

        depertmentbutton = view.findViewById(R.id.DepertmentButtonIDs);
        depertmentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_depertmrnt_layout();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }



        get_data();

        return view;
    }


    private void open_depertmrnt_layout(){


        MaterialAlertDialogBuilder Mdioloag = new MaterialAlertDialogBuilder(getActivity());
        View Mview = LayoutInflater.from(getActivity()).inflate(R.layout.profiel_dept_layout, null, false);

        ListView listView = Mview.findViewById(R.id.DeptList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.profile_dept_iteams, R.id.ProfileDeptText, depetment);
        listView.setAdapter(adapter);
        Mdioloag.setView(Mview);
        Mview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.reciver_message_animaction_fatout));

        AlertDialog alertDialog = Mdioloag.create();
        alertDialog.show();

    }

    private void get_data(){
        MuserDatavase.child(CurrentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            if(dataSnapshot.hasChild("Username")){
                                String name = dataSnapshot.child("Username").getValue().toString();
                                username.setText(name);
                            }

                            if(dataSnapshot.hasChild("Gender")){
                                String sex = dataSnapshot.child("Gender").getValue().toString();

                                if(sex.equals("Male")){
                                    male.setImageResource(R.drawable.personcolour);
                                }

                                if(sex.equals("Female")){
                                    female.setImageResource(R.drawable.womancolour);
                                }


                            }

                            if(dataSnapshot.hasChild("Depertment")){
                                String dept = dataSnapshot.child("Depertment").getValue().toString();
                                depttext.setText(dept);
                            }

                            if(dataSnapshot.hasChild("BloodGroup")){
                                String group = dataSnapshot.child("BloodGroup").getValue().toString();
                            }
                        }
                        else{

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}