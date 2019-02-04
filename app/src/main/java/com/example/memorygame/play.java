package com.example.memorygame;

import android.os.Parcel;
import android.os.Parcelable;

class play implements Parcelable {
    public String title;
    public String tries;
    public String time;
    public String type;

    play(){
    }
    ;

    play(String ti, String t, String l, String ty){
        title = ti;
        tries=t;
        time=l;
        type=ty;
    }

    protected play(Parcel in) {
        title = in.readString();
        tries = in.readString();
        time = in.readString();
        type = in.readString();
    }

    public static final Creator<play> CREATOR = new Creator<play>() {
        @Override
        public play createFromParcel(Parcel in) {
            return new play(in);
        }

        @Override
        public play[] newArray(int size) {
            return new play[size];
        }
    };

    @Override
    public String toString(){
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(tries);
        dest.writeString(time);
        dest.writeString(type);
    }
}

