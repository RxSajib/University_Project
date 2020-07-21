package ChatPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stamford.stamfordbloodbank.HomeContainer;
import com.stamford.stamfordbloodbank.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import All_PogoClass.MessageiteamsList;
import BootmNavPages.BottomHomePage;
import MessageAdapter.MessageAdapter;


public class ChatPages extends Fragment {

    private ImageView sendbutton;
    private EditText InputMessage;
    private ImageView attach;
    private ImageView bookmark;
    private Boolean isaddes = false;

    private DatabaseReference Mmessage_database;

    private ImageButton backbutton;

    private String ReciverID;

    private DatabaseReference MuserDatabase;
    private FirebaseAuth Mauth;
    private String SenderID;
    private MaterialTextView recivername;

    private String CurrentTime, Current_Date;
    private RecyclerView message_view;
    private List<MessageiteamsList> messageiteamsLists = new ArrayList<>();
    private MessageAdapter messageAdapter;


    public ChatPages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chat_page, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        } else {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }


        message_view = view.findViewById(R.id.MessageListViewID);
        message_view.setHasFixedSize(true);
        message_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessageAdapter(messageiteamsLists);
        message_view.setAdapter(messageAdapter);
        //  message_view.setBackgroundResource(R.drawable.chat_background);

        Mmessage_database = FirebaseDatabase.getInstance().getReference();
        recivername = view.findViewById(R.id.ChatUser);
        Mauth = FirebaseAuth.getInstance();
        SenderID = Mauth.getCurrentUser().getUid();
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        sendbutton = view.findViewById(R.id.SendButtonID);
        InputMessage = view.findViewById(R.id.InputMessageID);
        attach = view.findViewById(R.id.AssetsIconID);

        Bundle bundle = getArguments();
        ReciverID = bundle.getString("UID");


        bookmark = view.findViewById(R.id.BookmarButonID);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isaddes) {
                    bookmark.setImageResource(R.drawable.heart_white);
                    isaddes = false;
                } else {

                    bookmark.setImageResource(R.drawable.hear_selected);
                    isaddes = true;


                    open_internet_dioloag();

                }


            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] iteams = {

                        "Pdf File",
                        "Docx File"

                };


                MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(getActivity());
                Mbuilder.setItems(iteams, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {

                        }

                        if (i == 1) {

                        }
                    }
                });


                AlertDialog alertDialog = Mbuilder.create();
                alertDialog.show();
            }
        });


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = InputMessage.getText().toString().trim();

                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Please input any message", Toast.LENGTH_LONG).show();
                } else {
                    send_message(message);
                    InputMessage.setText(null);
                }
            }
        });

        backbutton = view.findViewById(R.id.backButtonID);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // goto_dashboard(new BottomHomePage());

                // getActivity().finishAffinity();
                //  getActivity().finish();
            }
        });


        if (!ReciverID.isEmpty()) {
            get_data();
        }

        read_message();

        return view;
    }


    private void send_message(String message) {

        String MessageSender_Ref = "Message/" + SenderID + "/" + ReciverID;
        String MessageReciver_Ref = "Message/" + ReciverID + "/" + SenderID;


        DatabaseReference user_message_key = Mmessage_database.child("Message").child(SenderID).child(ReciverID).push();

        String message_push_ID = user_message_key.getKey();

        Calendar calendar_time = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat_time = new SimpleDateFormat("HH:mm:a");
        CurrentTime = simpleDateFormat_time.format(calendar_time.getTime());


        Calendar calendar_date = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat_date = new SimpleDateFormat("yyyy-MM-dd");
        Current_Date = simpleDateFormat_date.format(calendar_date.getTime());

        Map<String, Object> messagemap = new HashMap<String, Object>();
        messagemap.put("Message", message);
        messagemap.put("Time", CurrentTime);
        messagemap.put("Date", Current_Date);
        messagemap.put("Type", "TEXT");
        messagemap.put("From", SenderID);

        Map<String, Object> messagemap_body = new HashMap<String, Object>();
        messagemap_body.put(MessageSender_Ref + "/" + message_push_ID, messagemap);
        messagemap_body.put(MessageReciver_Ref + "/" + message_push_ID, messagemap);


        Mmessage_database.updateChildren(messagemap_body)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

  /*  private void goto_dashboard(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomeContinerID, fragment);
        fragmentTransaction.commit();
    }*/

    private void get_data() {
        MuserDatabase.child(ReciverID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild("Username")) {
                                String name = dataSnapshot.child("Username").getValue().toString();
                                recivername.setText(name);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void read_message() {
        Mmessage_database.child("Message").child(SenderID).child(ReciverID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        MessageiteamsList message = dataSnapshot.getValue(MessageiteamsList.class);
                        messageiteamsLists.add(message);
                        messageAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void open_internet_dioloag() {


      /*View view = LayoutInflater.from(getActivity()).inflate(R.layout.no_connection, null, false);
      MaterialAlertDialogBuilder Mbuilder = new MaterialAlertDialogBuilder(getActivity());
      Mbuilder.setView(view);


      AlertDialog alertDialog = Mbuilder.create();
      alertDialog.show();*/

        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.no_connection);

        dialog.show();
    }
}