package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

public class MyMarker implements GoogleMap.OnMarkerClickListener {

    /*
        Marker color constant
    */
    public static final int COLOR_RED = 0;
    public static final int COLOR_AZURE = 210;
    public static final int COLOR_BLUE = 240;
    public static final int COLOR_CYAN = 180;
    public static final int COLOR_GREEN = 120;
    public static final int COLOR_MAGENTA = 300;
    public static final int COLOR_ORANGE = 30;
    public static final int COLOR_ROSE = 330;
    public static final int COLOR_VIOLET = 270;
    public static final int COLOR_YELLOW = 60;

    private GoogleMap mMap;
    private Location pos;
    private BitmapDescriptor markerIcon = null;
    private String title = null;
    private String snippet = null;
    private Context context;
    private Marker me;

    public MyMarker(GoogleMap mMap, Location pos, int icon, Context context) {
        this.mMap = mMap;
        this.pos = pos;
        this.context = context;
        changeMarkerIcon(icon);
        addMarker();
    }

    public MyMarker(GoogleMap mMap, int icon, Context context) {
        this.mMap = mMap;
        this.pos = mMap.getMyLocation();
        this.context = context;
        changeMarkerIcon(icon);
        addMarker();
    }

    public MyMarker(GoogleMap mMap, double latitude, double longitude, int icon, Context context) {
        Location bufPosition = new Location("");
        bufPosition.setLatitude(latitude);
        bufPosition.setLongitude(longitude);
        this.mMap = mMap;
        this.pos = bufPosition;
        this.context = context;
        changeMarkerIcon(icon);
        addMarker();
    }

    public double getMarkerLatitude() {
        return pos.getLatitude();
    }

    public double getMarkerLongitude() {
        return pos.getLongitude();
    }

    public Location getMarkerLocation() {
        return pos;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setMarkerIcon(BitmapDescriptor markerIcon) {
        this.markerIcon = markerIcon;
    }

    private void addMarker() {
        me = mMap.addMarker(new MarkerOptions()
                .icon(markerIcon)
                .position(new LatLng(pos.getLatitude(), pos.getLongitude()))
                .title(title)
                .snippet(snippet));

        mMap.setOnMarkerClickListener(this);
    }

    public void removeMarker() {
        me.remove();
    }

    public boolean onMarkerClick(Marker marker) {
        ItineraireTask task = new ItineraireTask(context, mMap, mMap.getMyLocation(), this);
        task.execute();
        return false;
    }

    public void changeMarkerIcon(int icon) {
        switch (icon) {
            case COLOR_RED :
            case COLOR_AZURE :
            case COLOR_BLUE :
            case COLOR_CYAN :
            case COLOR_GREEN :
            case COLOR_MAGENTA :
            case COLOR_ORANGE :
            case COLOR_ROSE :
            case COLOR_VIOLET :
            case COLOR_YELLOW :
                changeGoogleMarkerIconColor(icon);
                break;
            default:
                changeMarkerIconFromRessource(icon);
                break;
        }
    }

    public void changeGoogleMarkerIconColor(int color) {
        switch (color) {
            case COLOR_AZURE:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                break;
            case COLOR_BLUE:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                break;
            case COLOR_CYAN:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
                break;
            case COLOR_GREEN:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                break;
            case COLOR_MAGENTA:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                break;
            case COLOR_ORANGE:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                break;
            case COLOR_RED:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                break;
            case COLOR_ROSE:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
                break;
            case COLOR_VIOLET:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                break;
            case COLOR_YELLOW:
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                break;
        }
    }

    public void changeMarkerIconFromRessource(int icon) {
        markerIcon = BitmapDescriptorFactory.fromResource(icon);
    }
}
