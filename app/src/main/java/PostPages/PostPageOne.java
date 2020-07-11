package PostPages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stamford.stamfordbloodbank.R;

import java.util.HashMap;
import java.util.Map;

public class PostPageOne extends Fragment {

    private RelativeLayout firstpagebutton;
    private EditText pasent_name;
    private EditText pasent_number;
    private EditText pasent_address;
    private EditText hospital_name;

    private String pasent_inut, pasent_numberinput;
    private RelativeLayout continue_button;

    private DatabaseReference MpostSave;
    private FirebaseAuth Mauth;
    private String CurrentuserID;

    public PostPageOne(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.postpageone, container, false);

        MpostSave = FirebaseDatabase.getInstance().getReference().child("SavePost");
        Mauth = FirebaseAuth.getInstance();
        CurrentuserID = Mauth.getCurrentUser().getUid();

        firstpagebutton = view.findViewById(R.id.FirstPageButtonID);






        pasent_name = view.findViewById(R.id.PasentNameID);
        pasent_number = view.findViewById(R.id.PasentAddress);
        pasent_address = view.findViewById(R.id.PasentPhoneNumber);
        continue_button = view.findViewById(R.id.FirstPageButtonID);
        hospital_name = view.findViewById(R.id.PasentHospitalNameID);

     //   continue_button.setEnabled(false);




      /*  pasent_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                pasent_inut = editable.toString();


                if(!pasent_inut.isEmpty()){
                    pasent_number.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            pasent_numberinput = editable.toString();

                            if(!pasent_numberinput.isEmpty()){
                                pasent_address.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String address = editable.toString();
                                        if(!address.isEmpty()){
                                            firstpagebutton.setBackgroundResource(R.drawable.prifilebuton_visiable);

                                        }
                                        else {
                                            firstpagebutton.setBackgroundResource(R.drawable.profile_button_invisiabe);
                                            firstpagebutton.setEnabled(false);

                                        }
                                    }
                                });
                            }

                            else {
                                firstpagebutton.setBackgroundResource(R.drawable.profile_button_invisiabe);
                                firstpagebutton.setEnabled(false);
                            }

                        }
                    });
                }

                else {
                    firstpagebutton.setBackgroundResource(R.drawable.profile_button_invisiabe);
                    firstpagebutton.setEnabled(false);
                }


            }
        });*/






        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pasent_name_text = pasent_name.getText().toString();
                String hospital_text = hospital_name.getText().toString();
                String pasent_number_text = pasent_number.getText().toString();
                String pasent_address_text = pasent_address.getText().toString();

                if(pasent_name_text.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter pasent name", Toast.LENGTH_LONG).show();
                }
                else if(hospital_text.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter hospital name", Toast.LENGTH_LONG).show();

                }
                else if(pasent_number_text.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter contact number", Toast.LENGTH_LONG).show();

                }
                else if(pasent_address_text.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter address", Toast.LENGTH_LONG).show();

                }
                else {


                    continue_button.setEnabled(true);

                    final MaterialAlertDialogBuilder Mbulder = new MaterialAlertDialogBuilder(getActivity());
                    View Mview = LayoutInflater.from(getActivity()).inflate(R.layout.profile_progressbar, null, false);
                    Mbulder.setView(Mview);
                    Mbulder.setCancelable(false);
                    final AlertDialog alertDialog = Mbulder.create();
                    alertDialog.show();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(alertDialog.getWindow().getAttributes());
                    lp.width = 700;
                    lp.height = 1000;
                    alertDialog.getWindow().setAttributes(lp);




                    Map<String, Object> postmap = new HashMap<String, Object>();
                    postmap.put("pasent_name", pasent_name_text);
                    postmap.put("hospital_name", hospital_text);
                    postmap.put("pasent_number", pasent_number_text);
                    postmap.put("pasent_address", pasent_address_text);


                    MpostSave.child(CurrentuserID).updateChildren(postmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        alertDialog.dismiss();
                                        _goto_next_page(new PostPageTwo());
                                    }
                                    else {
                                        alertDialog.dismiss();
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    alertDialog.dismiss();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }

                /// _goto_next_page(new PostPageTwo());
            }
        });



        return view;
    }

    private void _goto_next_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.PostMainFreamID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void cheack_string(){
        /*Toast.makeText(getContext(), pasent_inut, Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), pasent_numberinput, Toast.LENGTH_LONG).show();*/

        if(!pasent_inut.isEmpty() && !pasent_numberinput.isEmpty()){


            continue_button.setEnabled(true);
        }
        else {
            continue_button.setEnabled(false);
        }
    }

}
