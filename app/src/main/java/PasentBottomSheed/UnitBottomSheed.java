package PasentBottomSheed;

import android.app.Activity;
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

public class UnitBottomSheed extends BottomSheetDialogFragment {

    private ListView listView;
    private String unitarray[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private RelativeLayout cancelbutton;
    private UnitbootmSheedLisiner mlisener;
    private DatabaseReference MpostSave;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.depertment_bottom_sheed, null, false);


        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        MpostSave = FirebaseDatabase.getInstance().getReference().child("SavePost");
        listView = view.findViewById(R.id.DepertmentListviewID);
        cancelbutton = view.findViewById(R.id.CancelButtonID);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ArrayAdapter<String> unitadapter = new ArrayAdapter<String>(getActivity(), R.layout.acadamic_info_test, R.id.SampleTextID, unitarray);
        listView.setAdapter(unitadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String unit = adapterView.getItemAtPosition(i).toString();
                MpostSave.child(CurrentUserID).child("unit_of_blood").setValue(unit);
                dismiss();
            }
        });

        return view;
    }


    public interface UnitbootmSheedLisiner{
        void onbuttomclick(String text);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {
            mlisener = (UnitbootmSheedLisiner) activity;
        }
        catch (ClassCastException e){

        }


    }
}
