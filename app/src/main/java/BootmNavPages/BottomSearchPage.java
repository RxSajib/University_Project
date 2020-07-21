package BootmNavPages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stamford.stamfordbloodbank.R;

import All_PogoClass.UsersList;
import de.hdodenhof.circleimageview.CircleImageView;


public class BottomSearchPage extends Fragment {

    private RecyclerView bloodlist;
    private DatabaseReference MuserDatabase;
    private String CurrentuserID;
    private FirebaseAuth Mauth;
    private MaterialCardView searchcontiner;

    public BottomSearchPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_search_page, container, false);

        searchcontiner = view.findViewById(R.id.SearchContinrID);
        searchcontiner.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.reciver_message_animaction_fatout));

        Mauth = FirebaseAuth.getInstance();
        CurrentuserID = Mauth.getCurrentUser().getUid();
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        bloodlist = view.findViewById(R.id.BloodListViewID);
        bloodlist.setHasFixedSize(true);
        bloodlist.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    @Override
    public void onStart() {

        FirebaseRecyclerAdapter<UsersList, BloodListHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersList, BloodListHolder>(
                UsersList.class,
                R.layout.user_card_design,
                BloodListHolder.class,
                MuserDatabase
        ) {
            @Override
            protected void populateViewHolder(final BloodListHolder bloodListHolder, UsersList usersList, int i) {
                String UID = getRef(i).getKey();

                MuserDatabase.child(UID)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.hasChild("Username")){
                                        String name = dataSnapshot.child("Username").getValue().toString();
                                        bloodListHolder.setUsernameset(name);
                                    }

                                    if(dataSnapshot.hasChild("Depertment")){
                                        String dept = dataSnapshot.child("Depertment").getValue().toString();
                                        bloodListHolder.setDeptset(dept);
                                    }

                                    if(dataSnapshot.hasChild("Imageuri")){
                                        String uri = dataSnapshot.child("Imageuri").getValue().toString();
                                        bloodListHolder.setProfileimageset(uri);
                                    }

                                    if(dataSnapshot.hasChild("BloodGroup")){
                                        String group = dataSnapshot.child("BloodGroup").getValue().toString();
                                        bloodListHolder.setBloodgroupset(group);
                                    }


                                }
                                else {

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        };

        bloodlist.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class BloodListHolder extends RecyclerView.ViewHolder{

        private Context context;
        private View Mview;
        private CircleImageView profileimage;
        private MaterialTextView username, dept, bloodgroup, number;

        public BloodListHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            context = Mview.getContext();
            username = Mview.findViewById(R.id.UserNameTextID);
            dept = Mview.findViewById(R.id.DeptID);
            bloodgroup = Mview.findViewById(R.id.Group);
            number = Mview.findViewById(R.id.UserNumber);
            profileimage = Mview.findViewById(R.id.ProfileImageIDs);
        }

        public void setUsernameset(String nam){
            username.setText(nam);
        }

        public void setDeptset(String dep){
            dept.setText(dep);
        }

        public void setBloodgroupset(String group){
            bloodgroup.setText(group);
        }

        public void setNumberset(String numb){
            number.setText(numb);
        }

        public void setProfileimageset(String img){
            Picasso.with(context).load(img).placeholder(R.drawable.person_design).into(profileimage);
        }
    }
}