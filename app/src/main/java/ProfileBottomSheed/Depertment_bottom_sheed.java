package ProfileBottomSheed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stamford.stamfordbloodbank.R;

public class Depertment_bottom_sheed extends BottomSheetDialogFragment {

    private String depetment[] = {"Business Administration", "Pharmacy", "Micro Biology", "Environmental Science", "English", "Economics", "Film and Media Studies", "Journalism and Media Studies",
            "Public Administration", "Law", "Computer Science", "Electrical & Electronic Engineering", "Civil Engineering", "Architecture"};
    private BottomsheedLisiner mlisiner;
    private DatabaseReference MuserDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.depertment_bottom_sheed, null, false);

        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        ListView listView = view.findViewById(R.id.DepertmentListviewID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.acadamic_info_test, R.id.SampleTextID, depetment);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = adapterView.getItemAtPosition(i).toString();


                MuserDatabase.child(CurrentUserID).child("depertment").setValue(subject);

                dismiss();
            }
        });






        return view;
    }



    public interface   BottomsheedLisiner{
        void onButtonClick(String mytext);
    }


    @Override
    public void onAttach(@NonNull Context context) {

        try {
            mlisiner = (BottomsheedLisiner) context;
        }
        catch (ClassCastException E){

        }


        super.onAttach(context);


    }
}
