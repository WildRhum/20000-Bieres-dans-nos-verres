package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

public abstract class GoogleRequestAPITask extends AsyncTask<Void, Integer, Boolean> {

    public static String TRACE_MODE = "walking";

    private Location myLocation;
    private MyMarker desitinationPosition;

    public GoogleRequestAPITask(Location myLocation, MyMarker desitinationPosition) {
        this.myLocation = myLocation;
        this.desitinationPosition = desitinationPosition;
    }

    protected abstract Boolean doInBackground(Void... params);

    protected Document request() {
        try {
            // API Url construction
            final StringBuilder url = new StringBuilder("http://maps.googleapis.com/maps/api/directions/xml?sensor=false");
            url.append(R.string.language);
            url.append("&origin=");
            url.append(myLocation.getLatitude());
            url.append(",");
            url.append(myLocation.getLongitude());
            url.append("&destination=");
            url.append(desitinationPosition.getMarkerLatitude());
            url.append(",");
            url.append(desitinationPosition.getMarkerLongitude());
            url.append("&mode");
            url.append(TRACE_MODE);

            // Call the web service
            InputStream inputStream = new URL(url.toString()).openStream();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setIgnoringComments(true);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();

            // Get the request status
            String status = document.getElementsByTagName("status").item(0).getTextContent();
            if (!"OK".equals(status)) {
                return null;
            }

            return document;

        } catch (final Exception e) {
            return null;
        }
    }
}
