package AllDrawerPages;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stamford.stamfordbloodbank.R;


public class StokePages extends Fragment {



    public StokePages() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stoke_pages, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        return view;
    }
}