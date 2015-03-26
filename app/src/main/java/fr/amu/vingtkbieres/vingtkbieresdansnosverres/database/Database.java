package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import android.animation.StateListAnimator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by legeek on 12/03/15.
 */
public class Database {
    private Connection connection;

    public Database( String urlConnection, String username, String password ) throws SQLException {
        connection = DriverManager.getConnection( urlConnection, username, password );
    }

    private String hashSHA_512( String str ) throws NoSuchAlgorithmException{
        String out = "";
        MessageDigest digest = MessageDigest.getInstance( "SHA-512" );
        digest.update(str.getBytes());

        byte[] data = digest.digest();

        for( int i = 0; i < data.length; ++i ) {
            String s = Integer.toHexString( new Byte( data[i] ) );
            while( s.length() < 2 ){
                s = "0" + s;
            }

            s = s.substring( s.length() - 2 );
            out += s;
        }

        return out;
    }

    /* ========== STYLE ========== */

    public Style getStyleById( int idStyle ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT text_style FROM STYLE WHERE ID_style=" + idStyle );

        if( resultSet.next() )
            return new Style( idStyle, resultSet.getString( "text_style" ) );
        else
            return null;
    }

    public List<Style> getAllStyle() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM STYLE ORDER BY ID_style ASC" );

        ArrayList<Style> list = new ArrayList<>();

        while( resultSet.next() ){
            list.add( new Style( resultSet.getInt( "ID_style" ), resultSet.getString( "text_style" ) ) );
        }

        return list;
    }

    /* ========== BEERS ========== */

    public Beer getBeerById( int id ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE ID_beer=" + id );

        if( resultSet.next() )
            return new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                                resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                                resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ).text,
                                resultSet.getString( "address_beer" ) );
        else
            return null;
    }

    public List<Beer> searchBeerByName( String name ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE name_beer LIKE '%" + name + "%'" );

        ArrayList<Beer> list = new ArrayList<>();

        while( resultSet.next() ) {
            list.add( new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                    resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                    resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ).text,
                    resultSet.getString( "address_beer" ) ) );
        }

        return list;
    }

    public List<Beer> searchBeerByStyle( int idStyle ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE style_beer=" + idStyle );

        ArrayList<Beer> list = new ArrayList<>();

        while( resultSet.next() )
        {
            list.add( new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                    resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                    resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ).text,
                    resultSet.getString( "address_beer" ) ) );
        }

        return list;
    }

    public List<Beer> searchBeerByBrewers( String brewers ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE brewers_beer LIKE '%" + brewers + "%'" );

        ArrayList<Beer> list = new ArrayList<>();

        while( resultSet.next() )
        {
            list.add( new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                    resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                    resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ).text,
                    resultSet.getString( "address_beer" ) ) );
        }

        return list;
    }


    /* ========== USER ========== */

    public User getUserById( int idUser ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM USER WHERE ID_user=" + idUser );

        if( resultSet.next() )
            return new User( resultSet.getInt( "ID_user" ), resultSet.getString( "firstname_user" ),
                    resultSet.getString( "lastname_user" ), resultSet.getString( "email_user" ) );
        else
            return null;
    }

    public boolean connectUser( String email, String mdp ) throws SQLException, NoSuchAlgorithmException {

        mdp = hashSHA_512( mdp );

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT COUNT(*) FROM USER WHERE email_user='" + email + "' AND pwd_user='" + mdp + "'" );

        resultSet.next();
        if( resultSet.getInt( 0 ) == 0 ) {
            return false;
        }
        else {
            return true;
        }
    }

    /* ========= rate ========== */

    public List<Rate> getUserRatedBeer( int idUser ) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM rate WHERE ID_user_rate=" + idUser);

        ArrayList<Rate> list = new ArrayList<>();
        User user = getUserById( idUser );

        while( resultSet.next() ){
            list.add( new Rate( getBeerById( resultSet.getInt( "ID_user_rate" ) ), user,
                                resultSet.getInt( "value_rate" ), resultSet.getString( "comment_rate" ) ) );
        }

        return list;
    }
}
