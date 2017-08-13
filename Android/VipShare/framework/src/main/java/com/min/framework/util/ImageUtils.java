package com.min.framework.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by minyangcheng on 2016/4/26.
 */
public class ImageUtils {

    public static boolean compressImageToFile(InputStream is,File outFile,int reqWidth,int reqHeight) {
        boolean flag=false;
        try {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
            options.inJustDecodeBounds=false;
            Bitmap bitmap=BitmapFactory.decodeStream(is, null, options);

            FileOutputStream fos=new FileOutputStream(outFile);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos)){
                flag=true;
            }
            fos.flush();
            fos.close();
            if(bitmap!=null&&!bitmap.isRecycled()){
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean compressImageToFile(File inFile,File outFile
            ,int reqWidth,int reqHeight) {
        return compressImageToFile(inFile,outFile,reqWidth,reqHeight,60);
    }

    public static boolean compressImageToFile(File inFile,File outFile
            ,int reqWidth,int reqHeight
            ,int quality) {
        boolean flag=false;
        try {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(inFile.getAbsolutePath(),options);
            options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
            options.inJustDecodeBounds=false;
            Bitmap bitmap=BitmapFactory.decodeFile(inFile.getAbsolutePath(),options);

            FileOutputStream fos=new FileOutputStream(outFile);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)){
                flag=true;
            }
            fos.flush();
            fos.close();
            if(bitmap!=null&&!bitmap.isRecycled()){
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static Bitmap rotaingImageView(int angle,Bitmap bitmap) {
        Matrix matrix = new Matrix();

        // 旋转图片 动作
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//		bitmap.recycle();
        return resizedBitmap;
    }

    public static void notifyGallery(Context context, String filePath) throws URISyntaxException {
        if(context==null&& TextUtils.isEmpty(filePath)){
            return;
        }
        File f = new File(filePath);
        if(f.exists()){
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        }
    }

    public static ShapeDrawable getShapeDrawable(int color,int height){
        ShapeDrawable shapeDrawable=new ShapeDrawable();
        shapeDrawable.setIntrinsicHeight(height);
        shapeDrawable.setIntrinsicWidth(height);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static boolean saveImageFile(Bitmap bitmap,File outFile){
        boolean flag=false;
        try {
            FileOutputStream fos=new FileOutputStream(outFile);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)){
                flag=true;
            }
        } catch (Exception e) {
        }
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
        return flag;
    }

}
