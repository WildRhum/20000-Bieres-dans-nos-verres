package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

/**
 * Created by legeek on 12/03/15.
 */
public class Beer {
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
}
