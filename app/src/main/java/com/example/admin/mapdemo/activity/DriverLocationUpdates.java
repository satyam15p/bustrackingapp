package com.example.admin.mapdemo.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.admin.mapdemo.R;
import com.example.admin.mapdemo.api.ApiClient;
import com.example.admin.mapdemo.api.ApiInterface;
import com.example.admin.mapdemo.model.BusHistoryModel2;
import com.example.admin.mapdemo.model.RealmData;
import com.example.admin.mapdemo.services.GPS_Service2;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverLocationUpdates extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String lat,log;
    private  double lat1, log1;
    private Marker mCurrentLocationMarker;
    private  static final String TAG  = DriverLocationUpdates.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;
    private DatabaseReference mDatabaseReference, mDemoRef, mChildLtRef, mChildRefLon ;
    private Button startLocationUpdates, stopLocationUpdates;
    private String driverName;
    private String busId;
    private Realm  realm;
    private String resultData;
    private  ApiInterface apiInterface;

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "In on resume methoe");
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e(TAG, "In on not null resume methoe");
                    //textView.append("\n" +intent.getExtras().get("coordinates"));
                    //  textView.append("\n" +intent.getExtras().get("Longitude")+"--"+intent.getExtras().get("Lattitude"));
                    Log.e(TAG+"Longitude:", String.valueOf(intent.getExtras().get("Longitude")));
                    Log.e(TAG+"Lattitude:", String.valueOf(intent.getExtras().get("Lattitude")));

                    mChildLtRef = mDemoRef.child("Lattitue");
                    mChildLtRef.setValue(intent.getExtras().get("Lattitude"));
                    mChildRefLon = mDemoRef.child("Longitude");
                    mChildRefLon.setValue(intent.getExtras().get("Longitude"));

                    String  slatti = String.valueOf(intent.getExtras().get("Longitude"));
                    String slang = String.valueOf(intent.getExtras().get("Lattitude"));

                    double  latti = Double.parseDouble(slatti);
                    double  lang = Double.parseDouble(slang);
                    //getAddress(78.44179902, 17.43503132);
                    //  getAddress(78.43348481, 17.43505146);
                    getAddress(lang,latti, driverName, busId );
                    //getAddress(17.4350336,78.43348481);
                    //getAddress(latti, lang);
                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "In on destroy  methoe");
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverlocationupdates);
        startLocationUpdates = findViewById(R.id.startUpdates);
        stopLocationUpdates = findViewById(R.id.stopUpdates);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        driverName = extras.getString("driverName");
        busId = extras.getString("busId");
        realm=Realm.getDefaultInstance();


        //ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDemoRef = mDatabaseReference.child(busId);
        mChildLtRef = mDemoRef.child("Lattitue");
        mChildRefLon = mDemoRef.child("Longitude");
        //driverName = "satyam parasa";
       // busId =  "10";
        // getAddress(17.4350336,78.43348481);
        if(!runtime_permissions())
            enable_buttons();

        getDatabase();

        stopLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                startLocationUpdates.setEnabled(true);
                Log.e(TAG, "stop button clicked");
                Intent i = new Intent(getApplicationContext(),GPS_Service2.class);
                stopService(i);

                Log.e("The result data is :", resultData);
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RealmData> call = apiInterface.sendData(resultData);
                call.enqueue(new Callback<RealmData>() {
                    @Override
                    public void onResponse(Call<RealmData> call, Response<RealmData> response) {
                        Log.e("The respose is", response.body().getData());
                        final RealmResults<BusHistoryModel2> results = realm.where(BusHistoryModel2.class).findAll();

// All changes to data must happen in a transaction
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                // Delete all matches
                                results.deleteAllFromRealm();
                                Log.e(TAG, "After delete");
                                RealmResults<BusHistoryModel2> realmResults=realm.where(BusHistoryModel2.class).findAll();
                                resultData =  new Gson().toJson(realm.copyFromRealm(realmResults));
                                Log.e("Data", new Gson().toJson(realm.copyFromRealm(realmResults)));

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<RealmData> call, Throwable t) {
                        Log.e("The Error is:", String.valueOf(t));
                    }
                });
            }
        });
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
    private void enable_buttons() {

        startLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLocationUpdates.setBackgroundColor(getResources().getColor(R.color.dimGray));
                startLocationUpdates.setEnabled(false);
                Log.e("status;", "in start updarebutton clicked");
                Intent i =new Intent(getApplicationContext(),GPS_Service2.class);
                startService(i);
            }
        });

//        stopLocationUpdates.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startLocationUpdates.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                startLocationUpdates.setEnabled(true);
//                Log.e(TAG, "stop button clicked");
//                Intent i = new Intent(getApplicationContext(),GPS_Service2.class);
//                stopService(i);
//
//            }
//        });
    }
    public  void getDatabase(){
        Log.e("status:", "we are in get database methode");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(busId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG+ "getDatabase methode", dataSnapshot.getKey());
                if (lat == null && log == null)
                {
                    lat ="17.43458822";
                    log =  "78.44169908";
                }
                else{
                lat = dataSnapshot.child("Lattitue").getValue().toString();
                lat1 = Double.parseDouble(lat);
                Log.e(TAG+ "getDatabase methode --Lattitue:::", dataSnapshot.child("Lattitue").getValue().toString());
                log = dataSnapshot.child("Longitude").getValue().toString();
                log1 = Double.parseDouble(log);
                Log.e(TAG+ "getDatabase methode Longitude", dataSnapshot.child("Longitude").getValue().toString());

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
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG +"Error", String.valueOf(databaseError));

            }
        });

    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }
    public void getAddress(double lat, double lan, String driverName, String budId){
        Log.e("status:", "we are in the get locaion methode");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lan, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
//            Toast.makeText(this, add, Toast.LENGTH_SHORT).show();
//            Log.v("IGA", "Address" + add);
//            Log.e("countryName", obj.getCountryName());
//            Log.e("getAdminArea", obj.getAdminArea());
////            Log.e("getPostalCode", obj.getPostalCode());
//            Log.e("counteCode", obj.getCountryCode());
//            Log.e("getSubAdminArea", obj.getLocality());
//            Log.e("Locality", obj.getLocality());
//            //Log.e("getSubTrough", obj.getSubThoroughfare());
            // Log.e("get loaction line", obj.getAddressLine(0));
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            // storeData();
            // TennisAppActivity.showDialog(add);
            String locationName =  obj.getAddressLine(0);
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            Log.e(TAG, "The date is :"+mydate);
            Log.e(TAG, "bus id"+busId+"drivername"+driverName+"");
         storeData(busId, driverName, mydate, locationName);



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void storeData(String busId, String driverName, String myDate, String locationName){
        //Log.e(TAG, "This is the methode in storedata");
        //String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Log.e(TAG, busId+"  "+ driverName+""+myDate+""+locationName);


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BusHistoryModel2 busHistoryModel1 = realm.createObject(BusHistoryModel2.class);
                busHistoryModel1.setBusId(busId);
                busHistoryModel1.setDate(myDate);
                busHistoryModel1.setTime(myDate);
                busHistoryModel1.setDriverName(driverName);
                busHistoryModel1.setLocationName(locationName);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<BusHistoryModel2> realmResults=realm.where(BusHistoryModel2.class).findAll();
                 resultData =  new Gson().toJson(realm.copyFromRealm(realmResults));
                Log.e("Data", new Gson().toJson(realm.copyFromRealm(realmResults)));
                Log.e(TAG, "The data is successfully inserted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, String.valueOf(error));
            }
        });


    }

}
