package com.example.benben.firstline.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by benben on 2016/5/30.
 */
public class DemoModel implements Parcelable{
    private String name;
    private int age;

    protected DemoModel(Parcel in) {
    }

    public static final Creator<DemoModel> CREATOR = new Creator<DemoModel>() {
        @Override
        public DemoModel createFromParcel(Parcel in) {
            DemoModel demoModel = new DemoModel(in);
            demoModel.name = in.readString();//读取name
            demoModel.age=in.readInt();//读取age
            return new DemoModel(in);
        }

        @Override
        public DemoModel[] newArray(int size) {
            return new DemoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);//写出name
        dest.writeInt(age);//写出age
    }
}
