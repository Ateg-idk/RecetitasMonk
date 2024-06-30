package com.example.recetitasmonk.fragmentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.recetitasmonk.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng trujillo = new LatLng(-8.1145531, -79.0216639);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(trujillo).title("sudado de pescado a la norteña"));
            /** marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trujillo));*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trujillo, 9));

            LatLng chiclayo = new LatLng(-6.7818639, -79.8490405);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions().position(chiclayo).title("arroz con pato"));
            /** marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trujillo));*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chiclayo, 9));

            LatLng cajamarca = new LatLng(-7.1606359, -78.5042023);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions().position(cajamarca).title("caldo de cabeza de cordero"));
            /** marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trujillo));*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cajamarca, 9));

            LatLng arequipa = new LatLng(-16.4040524, -71.5390115);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions().position(arequipa).title("rocoto relleno"));
            /** marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trujillo));*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa, 9));

            LatLng cusco = new LatLng(-13.5300193, -71.9392491);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.addMarker(new MarkerOptions().position(cusco).title("cuy al horno"));
            /** marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trujillo));*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cusco, 9));

            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 10);
            }
            else
                googleMap.setMyLocationEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}