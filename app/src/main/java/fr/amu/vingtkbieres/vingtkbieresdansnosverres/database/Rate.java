package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

/**
 * Created by legeek on 26/03/15.
 */
public class Rate {
    public final Beer beer;
    public final User user;
    public final int rate;
    public final String comment;

    public Rate(Beer beer, User user, int rate, String comment) {
        this.beer = beer;
        this.user = user;
        this.rate = rate;
        this.comment = comment;
    }
}
