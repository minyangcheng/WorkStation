package com.min.study;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.min.framework.util.EasyImageSelector;
import com.min.framework.util.L;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends AppCompatActivity {

    public static final String TAG="PhotoActivity_TEST";

    private EasyImageSelector mImageSelector;

    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        mImageSelector=new EasyImageSelector(this);
    }

    @OnClick(R.id.btn_camera)
    void clickCamear() {
        mDialog=new AlertDialog.Builder(this)
                .setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            mImageSelector.openCamera("min", PhotoActivity.this);
                            mDialog.dismiss();
                        }else{
                            mImageSelector.openGalleryPicker("yang",PhotoActivity.this);
                            mDialog.dismiss();
                        }
                    }
                }).create();
        mDialog.show();
    }

    @OnClick(R.id.btn_delete)
    void clickDelete(){
        mImageSelector.getCache().delete("min_1473740802904.jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageSelector.handleActivityResult(requestCode, resultCode, data, this, new EasyImageSelector.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImageSelector.ImageSource source) {
                L.d(TAG,"onImagePickerError...");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImageSelector.ImageSource source) {
                L.d(TAG,"imageFilePath=%s",imageFile.getAbsolutePath());
            }
        });
    }
}
