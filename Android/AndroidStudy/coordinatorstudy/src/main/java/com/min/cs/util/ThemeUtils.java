package com.min.cs.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.min.cs.R;
import com.min.framework.util.L;
import com.min.framework.util.SPUtils;

/**
 * Created by minyangcheng on 2016/10/19.
 */
public class ThemeUtils {

    public static final String KEY_THEME="theme";

    public static void setActivityTheme(Activity activity){
        if(activity==null) return;
        int themeResId=getThemeResId(activity);
        activity.setTheme(themeResId);
    }

    private static int getThemeResId(Context context){
        int themeType=SPUtils.getInt(KEY_THEME, 0);
        int themeResId=0;
        switch (themeType){
            case 0:
                themeResId= R.style.NormalTheme;
                break;
            case 1:
                themeResId= R.style.DayLightTheme;
                break;
            case 2:
                themeResId= R.style.DayNightTheme;
                break;
            default:
                themeResId= R.style.NormalTheme;
                break;
        }
        return themeResId;
    }

    public static void changeThemeFlag(){
        int index=SPUtils.getInt(KEY_THEME,0);
        index++;
        SPUtils.putInt(KEY_THEME, index%3);
    }

    public static Drawable getDrawable(Context context,int attrId){
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        Drawable drawable=a.getDrawable(0);
        a.recycle();
        return drawable;
    }

    public static int getColor(Context context,int attrId){
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        int color=a.getColor(0, Color.TRANSPARENT);
        a.recycle();
        return color;
    }

    public static int getDimensionPixelSize(Context context,int attrId){
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        int dimen=a.getDimensionPixelSize(0, 0);
        a.recycle();
        return dimen;
    }

    public static String getString(Context context,int attrId){
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        String s=a.getString(0);
        a.recycle();
        return s;
    }

}
