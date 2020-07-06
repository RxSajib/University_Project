package PostPages;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stamford.stamfordbloodbank.R;

public class PostPageOne extends Fragment {

    private RelativeLayout firstpagebutton;
    private EditText pasent_name;
    private EditText pasent_number;
    private EditText pasent_address;

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
        firstpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto_next_page(new PostPageTwo());
            }
        });


        pasent_name = view.findViewById(R.id.PasentNameID);
        pasent_number = view.findViewById(R.id.PasentAddress);
        pasent_address = view.findViewById(R.id.PasentPhoneNumber);
        continue_button = view.findViewById(R.id.FirstPageButtonID);

        continue_button.setEnabled(false);

        pasent_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                pasent_inut = editable.toString();
                MpostSave.child("PasentName").setValue(pasent_inut);

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
                                            firstpagebutton.setEnabled(true);
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
        });










        return view;
    }

    private void _goto_next_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.PostMainFreamID, fragment);
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
