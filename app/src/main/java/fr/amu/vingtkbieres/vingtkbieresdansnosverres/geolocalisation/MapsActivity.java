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
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;

    private Location myLocation;

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

    public void addMarker(Location pos, int markerIcon) {
        MyMarker marker = new MyMarker(mMap, pos, markerIcon, this);
    }

    public void addMarker(Location pos, String title, String snippet, int markerIcon) {
        MyMarker marker = new MyMarker(mMap, pos, markerIcon, this);
        marker.setTitle(title);
        marker.setSnippet(snippet);
    }

    public void addMarker(double latitude, double longitude, String title, int markerIcon) {
        MyMarker marker = new MyMarker(mMap, latitude, longitude, markerIcon, this);
        marker.setTitle(title);
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

    public void addMarkerFromAdress(String adr, String title, int icon) {
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
                this.addMarker(location.getLatitude(), location.getLongitude(), title, icon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
