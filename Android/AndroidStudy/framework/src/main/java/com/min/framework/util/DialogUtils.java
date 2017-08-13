package com.min.framework.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by minyangcheng on 2016/10/14.
 */
public class DialogUtils {

    public static void showItemsDialog(Context context,String title,String[] items,DialogInterface.OnClickListener listener){
        if(context==null) return;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        if(!StringUtils.isEmpty(title)){
            builder.setTitle(title);
        }
        if(!StringUtils.isEmpty(items)){
            builder.setItems(items,listener);
        }
        builder.show();
    }

    public static void showItemsDialog(Context context,int titleId,int itemsId,DialogInterface.OnClickListener listener){
        if(context==null) return;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        if(titleId>0){
            builder.setTitle(titleId);
        }
        if(itemsId>0){
            builder.setItems(itemsId, listener);
        }
        builder.show();
    }

}
