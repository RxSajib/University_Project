package PostPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.stamford.stamfordbloodbank.HomeContainer;
import com.stamford.stamfordbloodbank.R;

import java.util.HashMap;
import java.util.Map;

import PasentBottomSheed.PasentBlood_bottomSheed;
import PasentBottomSheed.UnitBottomSheed;
import ProfileBottomSheed.BloodBottomSheed;

public class PostPageTwo extends Fragment {

    private RelativeLayout pasentbloodunit;
    private RelativeLayout pasentbloodgroup;

    private MaterialTextView unitfirstname, unitlastname, unitmidname;

    private RelativeLayout malebutton, femailbutton;
    private ImageView maleimage, femaleimage;
    private DatabaseReference Msave_data;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private EditText usermessageinput;
    private RelativeLayout submitbutton;

    private RelativeLayout postbloodbutton;

    private DatabaseReference Mpost_data;


    private String pasent_name, pasent_hospital_name, unitof_blood, bloodgroup, location, pasent_number;

    public PostPageTwo() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.postpagetwo, container, false);




        submitbutton = view.findViewById(R.id.SubmitPostID);

        usermessageinput = view.findViewById(R.id.UserMessageID);

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        Msave_data = FirebaseDatabase.getInstance().getReference().child("SavePost");


        malebutton = view.findViewById(R.id.MaleButtonIDBox);
        femailbutton = view.findViewById(R.id.FemaleButtonBox);
        maleimage = view.findViewById(R.id.maleIcon);
        femaleimage = view.findViewById(R.id.Femaleicon);


        malebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                malebutton.setBackgroundResource(R.drawable.gender_box_clikc);
                femailbutton.setBackgroundResource(R.drawable.gender_box);
                Msave_data.child(CurrentUserID).child("pasent_gender").setValue("Male");
            }
        });

        femailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femailbutton.setBackgroundResource(R.drawable.gender_box_clikc);
                malebutton.setBackgroundResource(R.drawable.gender_box);
                Msave_data.child(CurrentUserID).child("pasent_gender").setValue("Female");
            }
        });

        pasentbloodgroup = view.findViewById(R.id.PasentBloodButtonID);
        pasentbloodunit = view.findViewById(R.id.PasentBloodUnit);
        pasentbloodunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitBottomSheed unitBottomSheed = new UnitBottomSheed();
                unitBottomSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        /// unite
        unitfirstname = view.findViewById(R.id.UnitFirstText);
        unitlastname = view.findViewById(R.id.UnitLastText);
        unitmidname = view.findViewById(R.id.UnitMidText);
        /// unite

        pasentbloodgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasentBlood_bottomSheed pasentBlood_bottomSheed = new PasentBlood_bottomSheed();
                pasentBlood_bottomSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });


        Mpost_data = FirebaseDatabase.getInstance().getReference().child("BloodRequest");


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = usermessageinput.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Message is empty", Toast.LENGTH_LONG).show();
                } else {
                    Msave_data.child(CurrentUserID).child("message").setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                Msave_data.child(CurrentUserID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            if(dataSnapshot.hasChild("pasent_name") && dataSnapshot.hasChild("hospital_name") && dataSnapshot.hasChild("pasent_number") && dataSnapshot.hasChild("pasent_address") && dataSnapshot.hasChild("unit_of_blood") && dataSnapshot.hasChild("Bloodgroup")){

                                                Toast.makeText(getContext(), "exists", Toast.LENGTH_LONG).show();
                                                pasent_name = dataSnapshot.child("pasent_name").getValue().toString();
                                                pasent_hospital_name = dataSnapshot.child("hospital_name").getValue().toString();
                                                pasent_number = dataSnapshot.child("pasent_number").getValue().toString();
                                                location = dataSnapshot.child("pasent_address").getValue().toString();
                                                unitof_blood = dataSnapshot.child("unit_of_blood").getValue().toString();
                                                bloodgroup = dataSnapshot.child("Bloodgroup").getValue().toString();


                                                save_all_data();
                                            }
                                        /*    else if(dataSnapshot.hasChild("hospital_name")){



                                            }
                                            else if(dataSnapshot.hasChild("pasent_number")){



                                            }
                                            else if(dataSnapshot.hasChild("pasent_address")){


                                            }
                                            else if(dataSnapshot.hasChild("unit_of_blood")){

                                            }
                                            else if(dataSnapshot.hasChild("Bloodgroup")){

                                            }*/


                                        }
                                        else {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });


                }
            }
        });

        return view;
    }

    private void save_all_data(){



            Toast.makeText(getContext(), "fire", Toast.LENGTH_LONG).show();

            Map<String, Object> postmap = new HashMap<String, Object>();
            postmap.put("pasent_name",pasent_name);
            postmap.put("hospital_name", pasent_hospital_name);
            postmap.put("pasent_number", pasent_number);
            postmap.put("location", location);
            postmap.put("unitof_blood", unitof_blood);
            postmap.put("bloodgroup", bloodgroup);

            Mpost_data.push().updateChildren(postmap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Msave_data.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            /// hear goto main page
                                            Intent intent = new Intent(getActivity(), HomeContainer.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }

}
