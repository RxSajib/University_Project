package BootmNavPages;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textview.MaterialTextView;
import com.stamford.stamfordbloodbank.R;


public class BottomHomePage extends Fragment {

    private RelativeLayout searchbutton;
    private RelativeLayout stokebutton;
    private RelativeLayout requestbutton;
    private RelativeLayout settingbutton;

    ///image control
    private ImageView searchimage, stokeimage, requestimage, settingimage;
    ///image control

    /// text control
    private MaterialTextView searchtext, stoketext, requesttext, settingtext;
    /// text control

    public BottomHomePage() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);


        searchbutton = view.findViewById(R.id.SearchButtonID);
        stokebutton = view.findViewById(R.id.StokeButtonID);
        requestbutton = view.findViewById(R.id.RequestButtonID);
        settingbutton = view.findViewById(R.id.SettingsButtonID);

        /// handle text
        searchtext = view.findViewById(R.id.SearhTextID);
        stoketext = view.findViewById(R.id.StokeTextID);
        requesttext = view.findViewById(R.id.DashBoardTextID);
        settingtext = view.findViewById(R.id.SettingsTextID);
        /// handle text

        searchimage = view.findViewById(R.id.BloodiconID);
        stokeimage = view.findViewById(R.id.BloodStoke);
        requestimage = view.findViewById(R.id.BloodRequest);
        settingimage = view.findViewById(R.id.Settingicon);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbutton.setBackgroundResource(R.drawable.home_menu_click);
                stokebutton.setBackgroundResource(R.drawable.more_design);
                requestbutton.setBackgroundResource(R.drawable.more_design);
                settingbutton.setBackgroundResource(R.drawable.more_design);


                searchimage.setImageResource(R.drawable.bloodsearch_white);
                stokeimage.setImageResource(R.drawable.injectionred);
                requestimage.setImageResource(R.drawable.postredicon);
                settingimage.setImageResource(R.drawable.settings);

                searchtext.setTextColor(getResources().getColor(R.color.white));
                stoketext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                requesttext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                settingtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
            }
        });

        stokebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbutton.setBackgroundResource(R.drawable.more_design);
                stokebutton.setBackgroundResource(R.drawable.home_menu_click);
                requestbutton.setBackgroundResource(R.drawable.more_design);
                settingbutton.setBackgroundResource(R.drawable.more_design);


                searchimage.setImageResource(R.drawable.bloodsearch_red);
                stokeimage.setImageResource(R.drawable.injectionwhite);
                requestimage.setImageResource(R.drawable.postredicon);
                settingimage.setImageResource(R.drawable.settings);

                searchtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                stoketext.setTextColor(getResources().getColor(R.color.white));
                requesttext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                settingtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
            }
        });

        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbutton.setBackgroundResource(R.drawable.more_design);
                stokebutton.setBackgroundResource(R.drawable.more_design);
                requestbutton.setBackgroundResource(R.drawable.home_menu_click);
                settingbutton.setBackgroundResource(R.drawable.more_design);

                searchimage.setImageResource(R.drawable.bloodsearch_red);
                stokeimage.setImageResource(R.drawable.injectionred);
                requestimage.setImageResource(R.drawable.postwhite_icon);
                settingimage.setImageResource(R.drawable.settings);

                searchtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                stoketext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                requesttext.setTextColor(getResources().getColor(R.color.white));
                settingtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
            }
        });

        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbutton.setBackgroundResource(R.drawable.more_design);
                stokebutton.setBackgroundResource(R.drawable.more_design);
                requestbutton.setBackgroundResource(R.drawable.more_design);
                settingbutton.setBackgroundResource(R.drawable.home_menu_click);

                searchimage.setImageResource(R.drawable.bloodsearch_red);
                stokeimage.setImageResource(R.drawable.injectionred);
                requestimage.setImageResource(R.drawable.postredicon);
                settingimage.setImageResource(R.drawable.settingwhite);

                searchtext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                stoketext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                requesttext.setTextColor(getResources().getColor(R.color.toolbarcolors));
                settingtext.setTextColor(getResources().getColor(R.color.white));
            }
        });

        return view;
    }



}