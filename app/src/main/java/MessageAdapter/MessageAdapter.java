package MessageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stamford.stamfordbloodbank.R;

import java.util.List;
import java.util.Map;

import All_PogoClass.MessageiteamsList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<MessageiteamsList> messageiteamsList;
    private DatabaseReference Mmessage_database;
    private FirebaseAuth Mauth;
    private String SenderID;

    public MessageAdapter(List<MessageiteamsList> messageiteamsList) {
        this.messageiteamsList = messageiteamsList;
    }



    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View message_layout_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_template, null, false);

        return new MessageHolder(message_layout_view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageHolder holder, int position) {

        Mmessage_database = FirebaseDatabase.getInstance().getReference().child("Message");
        Mauth = FirebaseAuth.getInstance();
        SenderID = Mauth.getCurrentUser().getUid();


      MessageiteamsList Usermessageposition =  messageiteamsList.get(position);
      String type = Usermessageposition.getType();
      String MessageFrom = Usermessageposition.getFrom();


      Mmessage_database.child(SenderID)
              .addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      if(dataSnapshot.exists()){
                          /// todo hear the user profile pic
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });



      holder.recivermessage_layout.setVisibility(View.GONE);
      holder.sendermessage_layout.setVisibility(View.GONE);

      if(type.equals("TEXT")){

          if (MessageFrom.equals(SenderID)) {

            /*  Mmessage_database.child(SenderID).orderByKey().limitToLast(1)
                      .addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              String message = dataSnapshot.child("Message").getValue().toString();
                              Toast.makeText(holder.context, message, Toast.LENGTH_SHORT).show();
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }
                      });
            */

              holder.sendermessage_layout.setVisibility(View.VISIBLE);
              holder.sendermessage_layout.setAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.send_message));
              holder.sendermessage.setText(Usermessageposition.getMessage());
              holder.sendermessage_time.setText(Usermessageposition.getTime());
          }
          else {
            holder.recivermessage_layout.setVisibility(View.VISIBLE);
              holder.recivermessage_layout.setAnimation(AnimationUtils.loadAnimation(holder.context, R.anim.reciver_message_animaction_fatout));
            holder.recivermessage.setText(Usermessageposition.getMessage());
            holder.recivermessage_time.setText(Usermessageposition.getTime());
          }

      }


    }

    @Override
    public int getItemCount() {
        return messageiteamsList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        private RelativeLayout sendermessage_layout, recivermessage_layout;
        private MaterialTextView sendermessage_time, recivermessage_time;
        private MaterialTextView sendermessage, recivermessage;
        private Context context;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            /// sender message
            sendermessage_layout = itemView.findViewById(R.id.SenderMessageLayoutID);
            sendermessage_time = itemView.findViewById(R.id.SenderMessageTime);
            sendermessage = itemView.findViewById(R.id.SenderMessae);
            /// sender message

            /// reciver message
            recivermessage_layout = itemView.findViewById(R.id.ReciverMessageLayout);
            recivermessage_time = itemView.findViewById(R.id.ReciverMessageTimeID);
            recivermessage = itemView.findViewById(R.id.ReciverMessage);
            /// reciver message


        }
    }
}
