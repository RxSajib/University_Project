package DonatePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stamford.stamfordbloodbank.R;


public class DonarPage extends Fragment {

    private RelativeLayout donate_button;
    private String Key_data;
    private CheckBox checkBox;
    private RelativeLayout Donatebutton;
    private DatabaseReference Mpost_data;
    private FirebaseAuth Mauth;
    private String  CurrentUserID;

    /// all view
    private MaterialTextView pasent_name, hospital_name, bloodgroup, hospital_address, phone_number;
    /// all view

    public DonarPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.donar_page, container, false);

        /// all view get
        pasent_name = view.findViewById(R.id.DetailsName);
        hospital_name = view.findViewById(R.id.HospitalNameID);
        bloodgroup = view.findViewById(R.id.BloodGroup);
        hospital_address = view.findViewById(R.id.HospitalAddressID);
        /// all view get


        Mpost_data = FirebaseDatabase.getInstance().getReference().child("BloodRequest");

        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        checkBox = view.findViewById(R.id.CheackBoxID);

        Bundle bundle = this.getArguments();
        Key_data = bundle.getString("UID");


        donate_button = view.findViewById(R.id.NowDonateButtonID);

        donate_button.setBackgroundResource(R.drawable.donate_uncheack);
        donate_button.setEnabled(false);
        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder Mbuider = new MaterialAlertDialogBuilder(getActivity());
                View myview = LayoutInflater.from(getActivity()).inflate(R.layout.congratulations_layout, null, false);

                Mbuider.setView(myview);

                Mbuider.show();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    donate_button.setBackgroundResource(R.drawable.inprogress_button_design);
                    donate_button.setEnabled(true);
                }
                else {
                    donate_button.setBackgroundResource(R.drawable.donate_uncheack);
                    donate_button.setEnabled(false);
                }
            }
        });



        read_data();
        return view;
    }


    private void read_data(){

        Mpost_data.child(Key_data)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                                if(dataSnapshot.hasChild("bloodgroup")){
                                    String blood = dataSnapshot.child("bloodgroup").getValue().toString();
                                    bloodgroup.setText(blood);
                                }

                                if(dataSnapshot.hasChild("hospital_name")){
                                    String hospitalname = dataSnapshot.child("hospital_name").getValue().toString();
                                    hospital_name.setText(hospitalname);
                                }

                                if(dataSnapshot.hasChild("location")){
                                    String locationget = dataSnapshot.child("location").getValue().toString();
                                    hospital_address.setText(locationget);
                                }

                                if(dataSnapshot.hasChild("pasent_number")){
                                    String number = dataSnapshot.child("pasent_number").getValue().toString();
                                }
                        }
                        else {
                            Toast.makeText(getActivity(), "data not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}