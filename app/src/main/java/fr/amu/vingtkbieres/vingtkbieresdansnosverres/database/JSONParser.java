package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by legeek on 04/04/15.
 */
public class JSONParser extends AsyncTask < String, Integer, JSONData > {
    @Override
    protected JSONData doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        try {
            response = client.execute(new HttpGet(params[0]));
            if( response.getStatusLine().getStatusCode() != HttpStatus.SC_OK ){
                return null;
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo( arrayOutputStream );

            JSONObject object = new JSONObject( arrayOutputStream.toString() );

            return new JSONData( object.getInt( "code" ), object.getString( "msgError" ), object.getJSONArray( "data" ) );
        }
        catch ( ClientProtocolException e ){
            e.printStackTrace();
            return null;
        }
        catch ( JSONException e ){
            e.printStackTrace();
            return null;
        }
        catch( IOException e ){
            e.printStackTrace();
            return null;
        }
    }
}
