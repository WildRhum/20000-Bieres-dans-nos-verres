package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by legeek on 12/03/15.
 */
public class Beer implements Parcelable{
    public final int overallScore;
    public final int styleScore;
    public final float abv;
    public final String name;
    public final String brewers;
    public final String style;
    public final String address;
    public final String country;

    public Beer(int overallScore, int styleScore, float abv, String name, String brewers, String style, String address) {
        this.overallScore = overallScore;
        this.styleScore = styleScore;
        this.abv = abv;
        this.name = name;
        this.brewers = brewers;
        this.style = style;

        if( address.contains( "USA" ) ){
            this.country = "USA";
        }
        else{
            this.country = address.substring( address.lastIndexOf( ',' ) + 1 ).trim();
        }

        this.address = address.replace( country, "" ).trim();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(overallScore);
        dest.writeInt(styleScore);
        dest.writeFloat(abv);
        dest.writeString(name);
        dest.writeString(brewers);
        dest.writeString(style);
        dest.writeString(address);
        dest.writeString(country);
    }

    public static final Parcelable.Creator<Beer> CREATOR = new Parcelable.Creator<Beer>()
    {
        @Override
        public Beer createFromParcel(Parcel source){
            return new Beer(source);
        }

        @Override
        public Beer[] newArray(int size){
            return new Beer[size];
        }
    };

    public Beer(Parcel in) {
        this.overallScore = in.readInt();
        this.styleScore = in.readInt();
        this.abv = in.readFloat();
        this.name = in.readString();
        this.brewers = in.readString();
        this.style = in.readString();
        this.address = in.readString();
        this.country = in.readString();
    }
}
