package com.example.sergiogarrido.citynet;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MapsContainerActivity extends FragmentActivity implements OnMapReadyCallback, OnMarkerClickListener {

    private GoogleMap mMap;
    JSONArray containers;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_container);
        String containersString = getIntent().getStringExtra("containers");
        try {
            System.out.println(containersString);
            containers = new JSONArray(containersString);
            System.out.println("Number of containers " + containers.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        mMap.setOnMarkerClickListener(this);
        Marker[] marker = new Marker[containers.length()]; //change length of array according to you

        for (int i= 0; i< containers.length(); i++)
        {
            try {
                LatLng tmpPosition = new LatLng(containers.getJSONObject(i).getDouble("latitude"), containers.getJSONObject(i).getDouble("longitude"));
                marker [i] = mMap.addMarker(new MarkerOptions().position(tmpPosition).title(containers.getJSONObject(i).getString("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(41.56331635, 2.22962594);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, "HAS CLICADO EN UN MARCADOR", Toast.LENGTH_LONG)
                .show();

        Intent i = new Intent();
        i.putExtra("marker", marker.getTitle());
        setResult(RESULT_OK, i);
        finish();
        return false;
    }
}
