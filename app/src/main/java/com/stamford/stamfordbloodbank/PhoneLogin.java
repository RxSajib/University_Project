package com.stamford.stamfordbloodbank;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.text.MeasuredText;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneLogin extends AppCompatActivity {

    private EditText countrycode, phonenumber;
    private RelativeLayout loginbutton;
    private FirebaseAuth Mauth;
    private HomePages homePages;
    private MaterialTextView numbertext;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks Mcallback;
    private PhoneAuthProvider.ForceResendingToken mResentToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_phone_login);

        homePages = new HomePages();

        numbertext = findViewById(R.id.NumberText);
        numbertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   goto_home_pages(new HomePages());
            }
        });

        Mauth = FirebaseAuth.getInstance();
        countrycode = findViewById(R.id.CodeNumberID);
        phonenumber = findViewById(R.id.PhoneNumberID);
        loginbutton =findViewById(R.id.LoginButtonID);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codetext = countrycode.getText().toString();
                String numbertext = phonenumber.getText().toString();
                String complected_phone_number = codetext+""+numbertext;

                if(codetext.isEmpty() || numbertext.isEmpty()){

                    final MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(PhoneLogin.this);
                    Mbuilder.setTitle("Error ...");
                    Mbuilder.setMessage("Please select your country code and phone number to continue");
                    Mbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alertDialog = Mbuilder.create();
                    alertDialog.show();
                }
                else {


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complected_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            PhoneLogin.this,
                            Mcallback
                    );
                }
            }
        });

        Mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
             //   signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                Log.d("ERROR", e.getMessage().toString());
            }

            @Override
            public void onCodeSent( String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);


                String data = s;
                gotootp_pages(data);

            }
        };


    }



    @Override
    public void onStart() {

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser != null){
            Intent intent = new Intent(getApplicationContext(), HomeContainer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onStart();
    }



 /*   private void goto_home_pages(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomeLayoutID, fragment);
        fragmentTransaction.commit();
    }*/

   /* private void goto_otp_pages(Fragment fragment, String data){

        Bundle bundle = new Bundle();
        bundle.putString("KEY", data);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomeLayoutID, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }*/

   private void gotootp_pages(String key){
       Intent intent = new Intent(getApplicationContext(), ReisterFragement.class);
       intent.putExtra("KEY", key);
       startActivity(intent);
   }


    /*private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Mauth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            gotohome_pages(new HomePages());
                            Intent intent = new Intent(getApplicationContext(), HomeContainer.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } else {


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(PhoneLogin.this);
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
    }*/
}