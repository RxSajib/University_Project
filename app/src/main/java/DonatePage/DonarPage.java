package DonatePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.stamford.stamfordbloodbank.R;


public class DonarPage extends Fragment {

    private RelativeLayout donate_button;

    public DonarPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.donar_page, container, false);

        donate_button = view.findViewById(R.id.NowDonateButtonID);
        donate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder Mbuider = new MaterialAlertDialogBuilder(getActivity());
                View myview = LayoutInflater.from(getActivity()).inflate(R.layout.congratulations_layout, null, false);

                Mbuider.setView(myview);

                Mbuider.show();
            }
        });

        return view;
    }
}