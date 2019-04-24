package com.example.admin.mapdemo;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String lat,log;
    private  double lat1, log1;
    private Marker mCurrentLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getDatabase();
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
        Log.e("status:", "we are in above onMapreadt metode");

        Log.e("Status2:", "We are in onMapReady methode");
    }
    public  void getDatabase(){
        Log.e("status:", "we are in get database methode");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("bus-1");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("resukt", dataSnapshot.getKey());
                lat = dataSnapshot.child("Lattitue").getValue().toString();
                lat1 = Double.parseDouble(lat);
                Log.e("Lattitue:::", dataSnapshot.child("Lattitue").getValue().toString());
                log = dataSnapshot.child("Longitude").getValue().toString();
                log1 = Double.parseDouble(log);
                Log.e("Logg", dataSnapshot.child("Longitude").getValue().toString());

                LatLng location = new LatLng(lat1, log1);

                if(mCurrentLocationMarker!=null){
                    mCurrentLocationMarker.setPosition(location);
                }else{
                    mCurrentLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.busicon))
                            .title("Bus is  Here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                    mMap.animateCamera(zoom);
                }

                // tv_loc.append("Lattitude: " + lattitude + "  Longitude: " + longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

                //stop location updates


//                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)).position(location));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
//                mMap.animateCamera(zoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
