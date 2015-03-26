package fr.amu.vingtkbieres.vingtkbieresdansnosverres.geolocalisation;

import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

public class LocationUpdatesManager implements LocationListener, GoogleMap.OnMyLocationChangeListener {

    public MapsActivity map;
    public boolean isBegin = true;

    public LocationUpdatesManager(MapsActivity map) {
        this.map = map;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMyLocationChange(Location location) {

        // Update camera position when user's position founded
        if (isBegin) {

            isBegin = false;

            map.setMyLocation(location);
            map.zoomPosition(map.getMyLocation(), 14);

            // It's just an exemple of uses
            map.addMarkerFromAdress("413 Avenue Gaston Berger 13625 Aix-en-Provence", "IUT AIX", MyMarker.COLOR_RED);
        }
    }
}