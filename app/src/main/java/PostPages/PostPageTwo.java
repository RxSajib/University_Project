package PostPages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.stamford.stamfordbloodbank.R;

import PasentBottomSheed.UnitBottomSheed;

public class PostPageTwo extends Fragment {

    private RelativeLayout pasentbloodunit;

    private MaterialTextView unitfirstname, unitlastname, unitmidname;

    public PostPageTwo(){

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.postpagetwo, container, false);

        pasentbloodunit = view.findViewById(R.id.PasentBloodUnit);
        pasentbloodunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitBottomSheed unitBottomSheed = new UnitBottomSheed();
                unitBottomSheed.show(getActivity().getSupportFragmentManager(), "open");
            }
        });

        /// unite
        unitfirstname = view.findViewById(R.id.UnitFirstText);
        unitlastname = view.findViewById(R.id.UnitLastText);
        unitmidname = view.findViewById(R.id.UnitMidText);
        /// unite

        return view;
    }
}
