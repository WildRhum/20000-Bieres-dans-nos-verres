package fr.amu.vingtkbieres.vingtkbieresdansnosverres.database;

/**
 * Created by legeek on 04/04/15.
 */
public class JSONDataException extends Exception {
    public JSONDataException( int code, String msg ){
        super( "" + code + msg );
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
