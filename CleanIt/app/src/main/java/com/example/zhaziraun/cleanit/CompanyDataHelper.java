package com.example.zhaziraun.cleanit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaziraun on 4/20/18.
 */

public class CompanyDataHelper implements Parcelable{
    int companyId;
    String companyName;
    String description;
    ArrayList<CleanType> cleanTypes = new ArrayList<>();

    public CompanyDataHelper(int companyId, String companyName, String description, ArrayList<CleanType> cleanTypes) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.description = description;
        this.cleanTypes = cleanTypes;
    }

    public CompanyDataHelper() {
    }

    protected CompanyDataHelper(Parcel in) {
        companyId = in.readInt();
        companyName = in.readString();
        description = in.readString();

        in.readTypedList(this.cleanTypes, CleanType.CREATOR);
    }

    public static final Creator<CompanyDataHelper> CREATOR = new Creator<CompanyDataHelper>() {
        @Override
        public CompanyDataHelper createFromParcel(Parcel in) {
            return new CompanyDataHelper(in);
        }

        @Override
        public CompanyDataHelper[] newArray(int size) {
            return new CompanyDataHelper[size];
        }
    };

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(companyId);
        parcel.writeString(companyName);
        parcel.writeString(description);
        parcel.writeTypedList(cleanTypes);
    }
}

