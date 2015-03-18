package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

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

    public Beer getBeerById( int id ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE ID_beer=" + id );

        if( resultSet.next() )
            return new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                                resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                                resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ),
                                resultSet.getString( "address_beer" ) );
        else
            return null;
    }

    public String getStyleById( int idStyle ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT libelle_style FROM STYLE WHERE ID_style=" + idStyle );

        resultSet.next();
        return resultSet.getString( "libelle_style" );
    }

    public List<Beer> searchBeerByName( String name ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE name_beer LIKE '%" + name + "%'" );

        ArrayList<Beer> list = null;

        while( resultSet.next() ) {
            list.add( new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                    resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                    resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ),
                    resultSet.getString( "address_beer" ) ) );
        }

        return list;
    }

    public List<Beer> searchBeerByStyle( int idStyle ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM BEER WHERE style_beer=" + idStyle );

        ArrayList<Beer> list = null;

        while( resultSet.next() )
        {
            list.add( new Beer( resultSet.getInt("overallScore_beer"), resultSet.getInt( "styleScore_beer" ),
                    resultSet.getFloat( "abv_beer" ), resultSet.getString( "name_beer" ),
                    resultSet.getString( "brewers_beer" ), getStyleById( resultSet.getInt( "style_beer" ) ),
                    resultSet.getString( "address_beer" ) ) );
        }

        return list;
    }
}
