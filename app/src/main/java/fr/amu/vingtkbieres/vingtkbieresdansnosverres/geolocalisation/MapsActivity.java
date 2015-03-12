package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import fr.amu.vingtkbieres.vingtkbieresdansnosverres.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;

    private Location myLocation;
    private BitmapDescriptor markerIcon;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public void changeMarkerIconFromRessource(int icon) {
        markerIcon = BitmapDescriptorFactory.fromResource(icon);
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

    public void addMarker(Location pos) {
        mMap.addMarker(new MarkerOptions()
                .icon(markerIcon)
                .position(new LatLng(pos.getLatitude(), pos.getLongitude())));
    }

    public void addMarker(Location pos, String title, String snippet) {
        mMap.addMarker(new MarkerOptions()
                .icon(markerIcon)
                .position(new LatLng(pos.getLatitude(), pos.getLongitude()))
                .title(title)
                .snippet(snippet));
    }

    public void addMarker(double latitude, double longitude, String title) {
        mMap.addMarker(new MarkerOptions()
                .icon(markerIcon)
                .position(new LatLng(latitude, longitude))
                .title(title));
    }

    private void setUpMap() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            buildAlertMessageNoGps();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,
                new LocationUpdatesManager(this));

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new LocationUpdatesManager(this));
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.gpsDisable)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    public GoogleMap getMap() {
        return this.mMap;
    }

    public void setMyLocation(Location loc) {
        this.myLocation = loc;
    }

    public Location getMyLocation() {
        return this.myLocation;
    }

    public void zoomPosition(Location loc, int zoom) {
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(loc.getLatitude(), loc.getLongitude()), zoom));
    }

    public void addMarkerFromAdress(String adr, String title) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(adr, 5);

            if (address == null)
                return;

            for (int i = 0; i < address.size(); ++i) {
                Address location = address.get(i);
                location.getLatitude();
                location.getLongitude();
                this.addMarker(location.getLatitude(), location.getLongitude(), title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
