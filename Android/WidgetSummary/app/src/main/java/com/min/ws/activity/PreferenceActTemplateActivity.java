package com.min.ws.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;
import com.min.ws.fragment.GeneralSettingFragment;
import com.min.ws.util.L;

public class PreferenceActTemplateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_act_template);

        getFragmentManager().beginTransaction()
                .replace(R.id.fl_content,new GeneralSettingFragment())
                .commit();
    }

}
