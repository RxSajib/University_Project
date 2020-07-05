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

public class Campus_Bottomshed extends BottomSheetDialogFragment {

    private String campus[] = {"Siddheswari", "Dhanmondi"};
    private DatabaseReference SaveDatabase;
    private FirebaseAuth Mauth;
    private String CurrentuserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.depertment_bottom_sheed, null, false);

        SaveDatabase = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
        Mauth = FirebaseAuth.getInstance();
        CurrentuserID = Mauth.getCurrentUser().getUid();

        RelativeLayout cancelbutton = view.findViewById(R.id.CancelButtonID);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ListView listView = view.findViewById(R.id.DepertmentListviewID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.acadamic_info_test, R.id.SampleTextID, campus);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String campusget = adapterView.getItemAtPosition(i).toString();
                SaveDatabase.child(CurrentuserID).child("campus").setValue(campusget);

                dismiss();
            }
        });

        return view;
    }
}
