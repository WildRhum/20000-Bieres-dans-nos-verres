package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

/**
 * Created by legeek on 26/03/15.
 */
public class User {
    private int idUser;
    private String firstname;
    private String lastname;
    private String email;

    public User(int idUser, String firstname, String lastname, String email) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}
