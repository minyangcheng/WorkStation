package com.min.fp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by minyangcheng on 2016/10/20.
 */
public class FragmentManagerUtil {

    public static void setFragment(FragmentManager fragmentManager,Fragment fragment,int id){
        fragmentManager.beginTransaction()
                .replace(id,fragment)
                .commit();
    }

    public static void putFragment(FragmentManager fragmentManager,Fragment fromFragment,Fragment toFragment,int id){
        fragmentManager.beginTransaction()
                .hide(fromFragment)
                .add(id, toFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void pop(FragmentManager fragmentManager,Fragment fragment){
//        fragmentManager.beginTransaction()
//                .remove(fragment)
//                .commit();
        fragmentManager.popBackStack();
    }

}
