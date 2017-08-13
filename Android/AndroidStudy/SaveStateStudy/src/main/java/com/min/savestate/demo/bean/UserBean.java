package com.min.savestate.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by minyangcheng on 2016/5/21.
 */
public class UserBean implements Parcelable {

    private String name;

    public UserBean(){
    }

    protected UserBean(Parcel in) {
        name=in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
