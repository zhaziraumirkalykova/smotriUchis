package com.example.zhaziraun.cleanit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaziraun on 4/20/18.
 */

public class CleanType implements Parcelable{
    int id;
    String name;
    double cost;

    public CleanType() {}

    public CleanType(int id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    protected CleanType(Parcel in) {
        id = in.readInt();
        name = in.readString();
        cost = in.readDouble();
    }

    public static final Creator<CleanType> CREATOR = new Creator<CleanType>() {
        @Override
        public CleanType createFromParcel(Parcel in) {
            return new CleanType(in);
        }

        @Override
        public CleanType[] newArray(int size) {
            return new CleanType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(cost);
    }
}
