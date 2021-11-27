package com.goldenapps.startshopping.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private TextView coordenadas;
    private Marker markerSelect;
    private double location1,location2;
    private LatLng miUbicacion;
    private Boolean markerSelectBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        coordenadas = findViewById(R.id.tv_coordenadas);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        getLocalizacion();
        mapFragment.getMapAsync(this);


    }

    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerDragListener(this);
        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                setLocation1(location.getLatitude());
                setLocation2(location.getLongitude());
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
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(markerSelect)){

        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerSelect)){
            coordenadas.setText("Latitud: "+marker.getPosition().latitude+" Longitud: "+marker.getPosition().longitude);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(markerSelect)){
            coordenadas.setText("Latitud: "+marker.getPosition().latitude+" Longitud: "+marker.getPosition().longitude);
        }
    }

    public double getLocation1() {
        return location1;
    }

    public void setLocation1(double location1) {
        this.location1 = location1;
    }

    public double getLocation2() {
        return location2;
    }

    public void setLocation2(double location2) {
        this.location2 = location2;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (markerSelectBoolean){
            miUbicacion = new LatLng(location1, location2);
            markerSelect = mMap.addMarker(new MarkerOptions().position(miUbicacion).draggable(true).title("ubicacion actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(miUbicacion)
                    .zoom(14)
                    .bearing(90)
                    .tilt(45)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            coordenadas.setText("Latitud: "+location1+" Longitud: "+location2);
            markerSelectBoolean = false;
        }else{
            coordenadas.setText("");
            markerSelect.remove();
            markerSelectBoolean = true;
        }

        return false;
    }
}