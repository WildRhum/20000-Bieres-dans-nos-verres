package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by legeek on 04/04/15.
 */
public class JSONData {
    private int code;
    private String error;
    private ArrayList<JSONObject> data;

    public JSONData(int code, String error, JSONArray data) throws JSONException {
        this.code = code;
        this.error = error;

        this.data = new ArrayList<>();
        for( int i = 0; i < data.length(); ++i )
            this.data.add( data.getJSONObject(i) );
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<JSONObject> getData() {
        return data;
    }

    protected void setValues( int code, String error, ArrayList<JSONObject> data) {
        this.code = code;
        this.error = error;
        this.data = data;
    }
}
