package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by legeek on 26/03/15.
 */
public class Style implements Parcelable {
    final public int id;
    final public String text;

    public Style(int id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
    }

    public static final Parcelable.Creator<Style> CREATOR = new Parcelable.Creator<Style>()
    {
        @Override
        public Style createFromParcel(Parcel source){
            return new Style(source);
        }

        @Override
        public Style[] newArray(int size){
            return new Style[size];
        }
    };

    public Style(Parcel in) {
        this.id = in.readInt();
        this.text = in.readString();
    }
}
