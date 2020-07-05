package ProfileBottomSheed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stamford.stamfordbloodbank.R;

public class AgeBottomSheed extends BottomSheetDialogFragment {

    private String age[] = {"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"};

    private DatabaseReference SaveData;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.depertment_bottom_sheed, null, false);

        RelativeLayout cancelbutton = view.findViewById(R.id.CancelButtonID);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        SaveData = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        ListView listView = view.findViewById(R.id.DepertmentListviewID);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.acadamic_info_test, R.id.SampleTextID, age);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String agetext = adapterView.getItemAtPosition(i).toString();

                SaveData.child(CurrentUserID).child("Age").setValue(agetext);
                dismiss();
            }
        });

        return view;

    }
}
