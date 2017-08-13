package com.min.easyrecycleview.util;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;

/**
 * Created by minyangcheng on 2016/6/23.
 */
public class DrawableUtil {

    public static ShapeDrawable getShapeDrawable(int color,int height){
        ShapeDrawable shapeDrawable=new ShapeDrawable();
        shapeDrawable.setIntrinsicHeight(height);
        shapeDrawable.setIntrinsicWidth(height);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

}
