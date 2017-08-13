package com.min.dbcompare.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by minyangcheng on 2016/8/1.
 */
public class UIUtils {

    public static void toast(Context context,String mess){
        Toast.makeText(context,mess,Toast.LENGTH_SHORT).show();
    }

}
