package ProfilePages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.stamford.stamfordbloodbank.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SetupProfile extends Fragment  {

    private EditText username;
    private RelativeLayout submitbutton;
    private CircleImageView profileimage;
    private Uri imageuri = null;
    private FirebaseAuth Mauth;
    private DatabaseReference SaveData;
    private String CurrentUserID;
    private StorageReference Mprofileimage;


    public SetupProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.setup_profile, container, false);

        Mprofileimage = FirebaseStorage.getInstance().getReference().child("Profileimage");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        SaveData = FirebaseDatabase.getInstance().getReference().child("SaveUsers");


        profileimage = view.findViewById(R.id.profileimageID);


        submitbutton = view.findViewById(R.id.ContinueButtonID);
        username = view.findViewById(R.id.ProfilenameID);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nametext = editable.toString();

                if (nametext.isEmpty()) {
                    submitbutton.setBackgroundResource(R.drawable.profile_button_invisiabe);
                    submitbutton.setEnabled(false);
                } else {
                    submitbutton.setBackgroundResource(R.drawable.prifilebuton_visiable);
                    submitbutton.setEnabled(true);


                }
            }
        });


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameget = username.getText().toString();
                if (usernameget.isEmpty()) {

                } else {

                    SaveData.child(CurrentUserID).child("UserName").setValue(usernameget);

                    goto_second_page(new ProfileSecondPage());
                }
            }
        });

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 511);

            }
        });





        get_data();
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 511 && resultCode == RESULT_OK) {
            Uri imageuri = data.getData();
            profileimage.setImageURI(imageuri);

            MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.profile_progressbar, null, false);
            Mbuilder.setView(view);
            final AlertDialog alertDialog = Mbuilder.create();
            alertDialog.show();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = 700;
            lp.height = 1000;
            alertDialog.getWindow().setAttributes(lp);

            StorageReference filepath = Mprofileimage.child(imageuri.getLastPathSegment());
            filepath.putFile(imageuri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                String downloaduri = task.getResult().getDownloadUrl().toString();
                                alertDialog.dismiss();
                                SaveData.child(CurrentUserID).child("profile_image_uri").setValue(downloaduri);
                            }
                            else {
                                alertDialog.dismiss();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    });




        }
    }


    private void goto_second_page(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slider_from_right    , R.anim.slide_outfrom_left);
        fragmentTransaction.replace(R.id.ProfileRootlayoutID, fragment);
        fragmentTransaction.commit();
    }

    private void get_data(){
            SaveData.child(CurrentUserID)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.hasChild("profile_image_uri")){
                                    String uri = dataSnapshot.child("profile_image_uri").getValue().toString();
                                    Picasso.with(getActivity()).load(uri).placeholder(R.drawable.person_design).into(profileimage);
                                }
                                if(dataSnapshot.hasChild("UserName")){
                                    String UserNameget = dataSnapshot.child("UserName").getValue().toString();
                                    username.setText(UserNameget);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
    }

}