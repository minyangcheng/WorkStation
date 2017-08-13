package com.min.savestate.demo.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.min.savestate.demo.R;
import com.min.savestate.demo.base.BaseFragment;

import java.util.List;

/**
 * Created by minyangcheng on 2016/5/21.
 */
public class UIUtils {

    public static void changeFragment(FragmentManager fm,List<BaseFragment> fragmentList,int index){
        FragmentTransaction ft=fm.beginTransaction();
        BaseFragment fragment=null;
        for(int i=0;i<fragmentList.size();i++){
            if(index==i){
                continue;
            }
            fragment=fragmentList.get(i);
            if(fragment!=null&&fragment.isAdded()&&!fragment.isHidden()){
                ft.hide(fragment);
            }
        }
        fragment=fragmentList.get(index);
        if(fragment!=null){
            if(fragment.isAdded()&&fragment.isHidden()){
                ft.show(fragment);
            }else if(!fragment.isAdded()){
                ft.add(R.id.view_content,fragment,fragment.tag);
            }
        }
        ft.commit();
    }

}
