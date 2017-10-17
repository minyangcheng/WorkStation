package com.min.core.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.min.common.util.FilePathUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class EasyImageSelector {

    private static final int REQ_PICK_PICTURE_FROM_GALLERY = 7458;
    private static final int REQ_PICK_PICTURE_FROM_CAMERA = 7459;

    private AmountLimitCache mCache;

    private int MAX_IAMGE_FILE_COUNT=3;  //保留的最大图片个数
    private String DIR_IMAGE="myPhotos";

    private String mPreffix="";
    private static String mSuffix=".jpg";
    private String mCameraImageUriStr;

    public enum ImageSource {
        GALLERY, CAMERA
    }

    public interface Callbacks {
        void onImagePickerError(Exception e, ImageSource source);

        void onImagePicked(File imageFile, ImageSource source);
    }

    public EasyImageSelector(Context context){
        File cacheDir=new File(FilePathUtil.getCacheRootDir(context),DIR_IMAGE);
        mCache=new AmountLimitCache(cacheDir.getAbsolutePath());
        mCache.setCacheSize(MAX_IAMGE_FILE_COUNT);
    }

    public void openCamera(String preffix,AppCompatActivity activity) {
        this.mPreffix=preffix;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image = getImageFile();
        Uri capturedImageUri = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        activity.startActivityForResult(intent, REQ_PICK_PICTURE_FROM_CAMERA);
        mCameraImageUriStr=capturedImageUri.toString();
    }

    public void openCamera(String preffix,Fragment fragment) {
        mPreffix=preffix;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image = getImageFile();
        Uri capturedImageUri = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        fragment.startActivityForResult(intent, REQ_PICK_PICTURE_FROM_CAMERA);
        mCameraImageUriStr=capturedImageUri.toString();
    }

    public void openGalleryPicker(String preffix,AppCompatActivity activity) {
        mPreffix=preffix;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQ_PICK_PICTURE_FROM_GALLERY);
    }

    public void openGalleryPicker(String preffix,Fragment fragment) {
        mPreffix=preffix;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, REQ_PICK_PICTURE_FROM_GALLERY);
    }


    private File takenCameraPicture() throws IOException, URISyntaxException {
        mCache.deleteFilesWhenArriveMaxCount();
        URI imageUri = new URI(mCameraImageUriStr);
        return new File(imageUri);
    }

    private File pickedGalleryPicture(Context context, Uri photoPath) throws IOException {
        InputStream pictureInputStream = context.getContentResolver().openInputStream(photoPath);
        File photoFile= getImageFile();
        mCache.save(pictureInputStream,photoFile.getName());
        return photoFile;
    }

    public File getImageFile(){
        File imageFile = mCache.getFile(getImageFileNameByTime());
        return imageFile;
    }

    private String getImageFileNameByTime(){
        return mPreffix+"_"+System.currentTimeMillis()+mSuffix;
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data, Context context, final Callbacks callbacks) {
        if(resultCode != AppCompatActivity.RESULT_OK){
            return;
        }
        if (requestCode == REQ_PICK_PICTURE_FROM_GALLERY && data != null) {
            Uri photoPath = data.getData();
            try {
                File photoFile = pickedGalleryPicture(context, photoPath);
                if(photoFile!=null){
                    callbacks.onImagePicked(photoFile, ImageSource.GALLERY);
                }else{
                    throw new Exception("copy image from gallery error");
                }
            } catch (Exception e) {
                callbacks.onImagePickerError(e, ImageSource.GALLERY);
            }
        } else if (requestCode == REQ_PICK_PICTURE_FROM_CAMERA) {
            try {
                File photoFile = takenCameraPicture();
                callbacks.onImagePicked(photoFile, ImageSource.CAMERA);
            } catch (Exception e) {
                callbacks.onImagePickerError(e, ImageSource.CAMERA);
            }
        }
    }

    public AmountLimitCache getCache(){
        return mCache;
    }

}
