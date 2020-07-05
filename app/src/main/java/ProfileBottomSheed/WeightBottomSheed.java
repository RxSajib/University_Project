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

public class WeightBottomSheed extends BottomSheetDialogFragment {

    private DatabaseReference Save_data;
    private FirebaseAuth Mauth;
    private String CurrentuserID;
    private String weight[] = { "30 KG", "31 KG", "32 KG", "33 KG", "34 KG", "35 KG", "36 KG", "37 KG", "38 KG", "39 KG", "40 KG"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.depertment_bottom_sheed, null, false);
        Save_data = FirebaseDatabase.getInstance().getReference().child("SaveUsers");
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.acadamic_info_test, R.id.SampleTextID, weight);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String widthget = adapterView.getItemAtPosition(i).toString();
                Save_data.child(CurrentuserID).child("Weight").setValue(widthget);
                dismiss();
            }
        });

        return view;
    }
}
