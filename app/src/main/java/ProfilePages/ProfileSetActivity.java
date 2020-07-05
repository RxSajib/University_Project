package ProfilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.stamford.stamfordbloodbank.R;

import ProfilePages.SetupProfile;

public class ProfileSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);

        setpage(new SetupProfile());
    }

    private void setpage(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ProfileRootlayoutID, fragment);
        fragmentTransaction.commit();
    }
}