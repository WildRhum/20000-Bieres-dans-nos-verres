package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

/**
 * Created by legeek on 26/03/15.
 */
public class User {
    public final int idUser;
    public final String firstname;
    public final String lastname;
    public final String email;

    public User(int idUser, String firstname, String lastname, String email) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
