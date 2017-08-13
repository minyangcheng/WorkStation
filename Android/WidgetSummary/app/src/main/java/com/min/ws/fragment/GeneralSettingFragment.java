package com.min.ws.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.min.ws.R;
import com.min.ws.util.UIUtils;

/**
 * Created by minyangcheng on 2016/9/5.
 */
public class GeneralSettingFragment extends PreferenceFragment {

    private EditTextPreference mNickNamePre;
    private Preference mClearCachePre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_preference);

        mNickNamePre= (EditTextPreference) findPreference("nike_name");
        mClearCachePre=findPreference("clear_cache");

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String nikeName=sp.getString("nike_name", "");
        if(!TextUtils.isEmpty(nikeName)){
            mNickNamePre.setSummary("昵称:"+nikeName);
        }
        mNickNamePre.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String nikeName=String.valueOf(newValue);
                if(!TextUtils.isEmpty(nikeName)){
                    mNickNamePre.setSummary("昵称:" + nikeName);
                }else{
                    mNickNamePre.setSummary("请输入你的昵称");
                }
                return true;
            }
        });

        mClearCachePre.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                UIUtils.toast(getActivity(), "清空缓存");
                return false;
            }
        });
    }
}
