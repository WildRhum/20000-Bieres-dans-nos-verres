package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

public class ItineraireTask extends AsyncTask<Void, Integer, Boolean> {

    public static String TRACE_MODE = "walking";

    private GoogleMap mMap;

    private Location myLocation;
    private MyMarker desitinationPosition;

    private Context context;

    private final ArrayList<LatLng> latLngArray = new ArrayList<LatLng>();

    public ItineraireTask(Context context, GoogleMap mMap, Location myLocation, MyMarker desitinationPosition) {
        this.context = context;
        this.mMap= mMap;
        this.myLocation = myLocation;
        this.desitinationPosition = desitinationPosition;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, R.string.directionBegin, Toast.LENGTH_LONG).show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // API Url construction
            final StringBuilder url = new StringBuilder("http://maps.googleapis.com/maps/api/directions/xml?sensor=false&language=");
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
            if(!"OK".equals(status)) {
                return false;
            }

            // Get points
            Element elementLeg = (Element) document.getElementsByTagName("leg").item(0);
            NodeList nodeList = elementLeg.getElementsByTagName("step");
            int size = nodeList.getLength();

            for(int i = 0; i < size; ++i) {
                Node nodeStep = nodeList.item(i);

                if(nodeStep.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodeStep;
                    decodePolylines(element.getElementsByTagName("points").item(0).getTextContent());
                }
            }

            return true;
        }
        catch(final Exception e) {
            return false;
        }
    }

    /*
        Method to get smoothed path :
        http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private void decodePolylines(String encodedPoints) {
        int index = 0;
        int lat = 0, lng = 0;

        while (index < encodedPoints.length()) {
            int b, shift = 0, result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            latLngArray.add(new LatLng((double)lat/1E5, (double)lng/1E5));
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result) {
            Toast.makeText(context, R.string.directionFounded, Toast.LENGTH_LONG).show();
        }
        else {
            PolylineOptions polylines = new PolylineOptions();
            polylines.color(context.getResources().getColor(R.color.brown));

            for(LatLng latLng : latLngArray) {
                polylines.add(latLng);
            }

            desitinationPosition.removeMarker();

            // Zoom to the destination point
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngArray.get(latLngArray.size() - 1), 14));
            mMap.addPolyline(polylines);

            double latitude = latLngArray.get(latLngArray.size() - 1).latitude;
            double longitude = latLngArray.get(latLngArray.size() - 1).longitude;

            new MyMarker(mMap, latitude, longitude, MyMarker.COLOR_ORANGE, context);
        }
    }
}