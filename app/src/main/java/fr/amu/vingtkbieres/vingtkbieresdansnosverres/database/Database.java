package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by legeek on 12/03/15.
 */
public class Database {
    static private JSONParser parser = new JSONParser();

    static final private String BASE_URL               = "http://20kbieres.ddns.net/web.php";

    static final private String CODE_STYLE_BY_ID       = "0";
    static final private String CODE_STYLE_ALL         = "1";

    static final private String CODE_BEER_BY_ID        = "10";
    static final private String CODE_BEER_BY_NAME      = "11";
    static final private String CODE_BEER_BY_STYLE     = "12";
    static final private String CODE_BEER_BY_BREWERS   = "13";

    static final private String CODE_USER_BY_ID        = "20";
    static final private String CODE_USER_CONNECT      = "21";

    static final private String CODE_RATE_BY_USER      = "30";

    static final private String CODE_ACHIEVEMENT_USER  = "40";


    static private String hashSHA_512( String str ) throws NoSuchAlgorithmException{
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

    static private String generateUrl( String codeAction ){
        return generateUrl( codeAction, null, null );
    }
    static private String generateUrl( String codeAction, String p1 ){
        return generateUrl( codeAction, p1, null );
    }
    static private String generateUrl( String codeAction, String p1, String p2 ){
        return generateUrl( codeAction, p1, p2, null );
    }
    static private String generateUrl( String codeAction, String p1, String p2, String p3 ){
        String url = BASE_URL + "?action=" + codeAction;

        if( p1 != null ){
            url += "&p1=" + p1;

            if( p2 != null ){
                url += "&p2=" + p2;

                if( p3 != null){
                    url += "&p3=" + p3;
                }
            }
        }

        return url;
    }

    static private boolean testJSONData(JSONData data) throws JSONDataException {
        if( data == null )
            return false;
        else if( data.getCode() != 0 )
            throw new JSONDataException( data.getCode(), data.getError() );

        return true;
    }

    /* ========== STYLE ========== */

    static public Style getStyleById( int idStyle ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_STYLE_BY_ID, String.valueOf( idStyle ) ) );

        if( !testJSONData(data) )
            return null;

        return new Style( data.getCode(), data.getData().get(0).getString( "text_style" ) );
    }

    static public List<Style> getAllStyle() throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_STYLE_ALL ) );

        if( !testJSONData( data ) )
            return null;

        ArrayList<Style> list = new ArrayList<>();

        for( JSONObject obj : data.getData() )
            list.add(new Style(obj.getInt("ID_style"),
                    obj.getString("text_style")));

        return list;
    }

    /* ========== BEERS ========== */

    static public Beer getBeerById( int id ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_BEER_BY_ID, String.valueOf(id) ) );
        
        if( testJSONData( data ) )
            return new Beer( data.getData().get(0).getInt("overallScore_beer"), data.getData().get(0).getInt("styleScore_beer"),
                                (Float) data.getData().get(0).get( "abv_beer" ), data.getData().get(0).getString( "name_beer" ),
                                data.getData().get(0).getString( "brewers_beer" ), getStyleById( data.getData().get(0).getInt( "style_beer" ) ).text,
                                data.getData().get(0).getString( "address_beer" ) );
        else
            return null;
    }

    static public List<Beer> searchBeerByName( String name ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_BEER_BY_NAME, name ) );

        ArrayList<Beer> list = new ArrayList<>();
        for( JSONObject obj : data.getData() ) {
            list.add(new Beer(obj.getInt("overallScore_beer"), obj.getInt("styleScore_beer"),
                    Float.parseFloat((String) (obj.get( "abv_beer" ))), obj.getString("name_beer"),
                    obj.getString("brewers_beer"), getStyleById(obj.getInt("style_beer")).text,
                    obj.getString("address_beer")));
        }

        return list;
    }

    static public List<Beer> searchBeerByStyle( int idStyle, int startLimit, int numberLimit ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_BEER_BY_STYLE, String.valueOf(idStyle ), String.valueOf( startLimit ), String.valueOf( numberLimit ) ) );

        if( !testJSONData( data ) )
            return null;

        List<Beer> list = new ArrayList<>();
        for( JSONObject obj : data.getData() )
        {
            list.add( new Beer( obj.getInt("overallScore_beer"), obj.getInt( "styleScore_beer" ),
                    Float.parseFloat((String) (obj.get( "abv_beer" ))), obj.getString( "name_beer" ),
                    obj.getString( "brewers_beer" ), getStyleById( obj.getInt( "style_beer" ) ).text,
                    obj.getString("address_beer") ) );
        }

        return list;
    }

    static public List<Beer> searchBeerByBrewers( String brewers ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_BEER_BY_BREWERS, String.valueOf( brewers ) ) );

        if( !testJSONData( data ) )
            return null;

        ArrayList<Beer> list = new ArrayList<>();
        for( JSONObject obj : data.getData() )
        {
            list.add( new Beer( obj.getInt("overallScore_beer"), obj.getInt( "styleScore_beer" ),
                    (Float)obj.get( "abv_beer" ), obj.getString( "name_beer" ),
                    obj.getString( "brewers_beer" ), getStyleById( obj.getInt( "style_beer" ) ).text,
                    obj.getString( "address_beer" ) ) );
        }

        return list;
    }


    /* ========== USER ========== */

    static public User getUserById( int idUser ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_USER_BY_ID, String.valueOf( idUser ) ) );

        if( testJSONData( data ) )
            return new User( data.getData().get( 0 ).getInt( "ID_user" ), data.getData().get( 0 ).getString( "firstname_user" ),
                    data.getData().get( 0 ).getString( "lastname_user" ), data.getData().get( 0 ).getString( "email_user" ) );
        else
            return null;
    }

    static public int connectUser( String email, String mdp ) throws NoSuchAlgorithmException, JSONDataException, JSONException {
        mdp = hashSHA_512( mdp );

        JSONData data = parser.parseFromUrl( generateUrl( CODE_USER_CONNECT, email, mdp ) );

        if( !testJSONData( data ) || data.getData().isEmpty() ) {
            return -1;
        }
        else {
            return data.getData().get(0).getInt( "ID_user" );
        }
    }

    /* ========= rate ========== */

    static public List<Rate> getUserRatedBeer( int idUser ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_RATE_BY_USER, String.valueOf( idUser ) ) );

        ArrayList<Rate> list = new ArrayList<>();
        User user = getUserById( idUser );

        for( JSONObject obj : data.getData() ){
            list.add( new Rate( getBeerById( obj.getInt( "ID_user_rate" ) ), user,
                                obj.getInt( "value_rate" ), obj.getString( "comment_rate" ) ) );
        }

        return list;
    }

    static public List<Achievement> loadAchievements ( int idUser, Context c ) throws JSONException, JSONDataException {
        JSONData data = parser.parseFromUrl( generateUrl( CODE_ACHIEVEMENT_USER, String.valueOf( idUser ) ) );

        ArrayList<Achievement> list = new ArrayList<>();

        if( !testJSONData( data ) )
            return null;

        for( JSONObject obj : data.getData() )
            list.add(new Achievement(c, obj.getString("title_achiev"), obj.getInt("reach_achiev"),
                    obj.getInt("value_progress"), obj.getString("desc_achiev")));

        return list;
    }
}
