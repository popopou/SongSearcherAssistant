package zsx.com.songsearchassistant.activity;

import android.preference.PreferenceActivity;

import java.util.List;

import zsx.com.songsearchassistant.R;
import zsx.com.songsearchassistant.fragment.SettingFragment;

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingFragment.class.getName().equals(fragmentName);
    }
}
