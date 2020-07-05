package com.stamford.stamfordbloodbank;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class ReisterFragement extends AppCompatActivity {


    private RelativeLayout loginbuttonID;
    private EditText numberone, numbertwo, numberthree;
    private EditText numberfour, numberfive, numbersix;
    private String fullotp_code;

    private FirebaseAuth Mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reister_fragement);

        Mauth = FirebaseAuth.getInstance();


        final String aauthkey = getIntent().getStringExtra("KEY");

        /*Bundle bundle = this.getArguments();
        final String aauthkey = bundle.getString("KEY");*/


        loginbuttonID = findViewById(R.id.LoginButtonID);

        /// set all number value
        numberone = findViewById(R.id.InputNumberOne);
        numbertwo = findViewById(R.id.InputNumberTwo);
        numberthree = findViewById(R.id.InputNumberThree);
        numberfour = findViewById(R.id.InputNumberFour);
        numberfive = findViewById(R.id.InputNumberFive);
        numbersix = findViewById(R.id.InputNumberSix);
        /// set all number value


        loginbuttonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numberoneget = numberone.getText().toString();
                String numbertwoget = numbertwo.getText().toString();
                String numberthreeget = numberthree.getText().toString();
                String numberfourget = numberfour.getText().toString();
                String numberfiveget = numberfive.getText().toString();
                String numbersixget = numbersix.getText().toString();


                fullotp_code = numberoneget + numbertwoget + numberthreeget + numberfourget + numberfiveget + numbersixget;

                if (numberoneget.isEmpty() || numbertwoget.isEmpty() || numberthreeget.isEmpty() || numberfourget.isEmpty() || numberfiveget.isEmpty() || numbersixget.isEmpty()) {

                    final MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(ReisterFragement.this);
                    Mbuilder.setTitle("Error ...");
                    Mbuilder.setMessage("Please enter all otp code and continue");
                    Mbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alertDialog = Mbuilder.create();
                    alertDialog.show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(aauthkey, fullotp_code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });


    }





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Mauth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            gotohome_pages(new HomePages());
                            Intent intent = new Intent(getApplicationContext(), HomeContainer.class);
                            startActivity(intent);


                        } else {


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(ReisterFragement.this);
                                Mbuilder.setTitle("Server Error ...");
                                Mbuilder.setMessage(task.getException().getMessage());

                                Mbuilder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                AlertDialog alertDialog = Mbuilder.create();
                                alertDialog.show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onStart() {

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
         //   gotohome_pages(new HomePages());
            Intent intent = new Intent(getApplicationContext(), HomeContainer.class);
            startActivity(intent);
        }

        super.onStart();
    }

  /*  private void gotohome_pages(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.OptRootID, fragment);
        fragmentTransaction.commit();
    }*/
}