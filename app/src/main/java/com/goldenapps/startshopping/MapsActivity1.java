package com.goldenapps.startshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.goldenapps.startshopping.databinding.ActivityMaps1Binding;

import java.util.Locale;

public class MapsActivity1 extends AppCompatActivity implements  OnMapReadyCallback,GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private ActivityMaps1Binding binding;
    private Marker markerPrueba,markerDrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng Coquimbo = new LatLng(-29.90453, -71.24894);
        mMap.addMarker(new MarkerOptions().position(Coquimbo).title("StartShopping").snippet("Bienvenido a StartShopping")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.coquimbo)));


        LatLng prueba = new LatLng(-29.9043719262181, -71.24863631949397);
        markerPrueba = googleMap.addMarker(new MarkerOptions()
                .position(prueba)
                .title("Prueba")
        );

        LatLng NuevaDireccion = new LatLng(-29.90433634389654, -71.2493939851436);
        markerPrueba = googleMap.addMarker(new MarkerOptions()
                .position(prueba)
                .title("NuevaDireccion")
                .draggable(true)
        );
        //Camara
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Coquimbo));
        //Click en el Marcador
        googleMap.setOnMarkerClickListener(this);
        //Arrastrar el Marcador
        googleMap.setOnMarkerDragListener(this);
}

    @Override

    public boolean onMarkerClick(Marker marker) {
    if(marker.equals(markerPrueba)){
        String lat,lng;
        lat = Double.toString(marker.getPosition().latitude);
        lng = Double.toString(marker.getPosition().longitude);
        Toast.makeText(this,"Mi primer evento",Toast.LENGTH_SHORT).show();


    }
        return false;
    }


    @Override
    public void onMarkerDrag( Marker marker) {
        if(marker.equals(markerDrag)){
            String newTitle = String.format(Locale.getDefault(),
                    getString(R.string.marker_detail_latlng),
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);


            setTitle(newTitle);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
     if(marker.equals(markerDrag)){
         Toast.makeText(this,"Finish",Toast.LENGTH_LONG).show();
         setTitle(R.string.sitio);
     }
    }

    @Override
    public void onMarkerDragStart( Marker marker) {
        if(marker.equals(markerDrag)){
            Toast.makeText(this,"Start",Toast.LENGTH_LONG).show();
        }

}
}