package edu.smarthealthcare.smarthealthcareapp;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.DrugLocationModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.KioskModel;
import edu.smarthealthcare.smarthealthcareapp.Utils.APIService;
import edu.smarthealthcare.smarthealthcareapp.Utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SearchView.OnQueryTextListener {

    private GoogleMap mGoogleMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker lastLocationMarker;
    public static final int REQUEST_LOCTION_CODE = 99;
    private ProgressDialog progressDialog;

    private SearchView simpleSearchView;

    private List<KioskModel> kioskModelList = new ArrayList<KioskModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kiosk Locations");


        if (googlePlayServiceAvaiable()) {

            initMap();

            simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView); // inititate a search view
            CharSequence query = simpleSearchView.getQuery(); // get the query string currently in the text field
            simpleSearchView.setOnQueryTextListener(this);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        searchKioskByDrugName(query+"");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (s.isEmpty()|| s.equals("")){
            getKioskLocation();
        }
        //Log.d("SEARCH_VALUE : " , s+"");
        return false;
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog(MapActivity.this);
        progressDialog.show();
        progressDialog.setTitle("Please wait!!");
        progressDialog.setMessage("Please wait!!");
        progressDialog.setCancelable(false);
        getKioskLocation();

    }

    private void getKioskLocation() {

        kioskModelList.clear();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<KioskModel>> call = apiService.getKioskLocation();

        call.enqueue(new Callback<List<KioskModel>>() {
            @Override
            public void onResponse(Call<List<KioskModel>> call, Response<List<KioskModel>> response) {

                if (response.isSuccessful()){

                    if (response.body().isEmpty()){

                        new MaterialDialog.Builder(MapActivity.this)
                                .title("No data found")
                                .content("Sorry, We can not find relavent data in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(MapActivity.this, R.color.material_green))
                                .build().show();
                        progressDialog.cancel();

                    }else{

                        kioskModelList.clear();
                        kioskModelList.addAll(response.body());

                        showMarkers();

                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(MapActivity.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<List<KioskModel>> call, Throwable t) {
                Toast.makeText(MapActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showMarkers() {

        mGoogleMap.clear();
        double lat = 0.0;
        double lng = 0.0;
        String location[] = null;
        Log.d("MARKER", "Came here");
        for (KioskModel kioskModel : kioskModelList) {

            Log.d("MARKER_AVAIL", kioskModelList.size()+"");

            location = kioskModel.getLocation().split(",");
            lat = Double.parseDouble(location[0]);
            lng = Double.parseDouble(location[1]);

            mGoogleMap.addMarker(new MarkerOptions()
                    .title("Kiosk >> " + kioskModel.getKioskId().toString() + " in " + kioskModel.getAddress().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(new LatLng(lat,lng))
                    .snippet(kioskModel.getAddress().toString()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_LOCTION_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                        if (client == null){
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }else{
                        Toast.makeText(this,"Permission Denied", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
        }
    }

    public boolean googlePlayServiceAvaiable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can not connect to the play services", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    private synchronized void  buildGoogleApiClient(){

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if (lastLocationMarker != null){
            lastLocationMarker.remove();
        }else{

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Your Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            lastLocationMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

            if (client != null){
                LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
        }
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCTION_CODE);

            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCTION_CODE);
            }
            return false;
        }else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void searchKioskByDrugName(String drug_name){

        Log.d("SEARCH_SUBMIT_VALUE : " , drug_name);
        kioskModelList.clear();
        APIService apiService = ServiceGenerator.createService(APIService.class);
        Call<List<KioskModel>> call = apiService.getKioskLocationByDrugName(drug_name);

        call.enqueue(new Callback<List<KioskModel>>() {
            @Override
            public void onResponse(Call<List<KioskModel>> call, Response<List<KioskModel>> response) {
                if (response.isSuccessful()){

                    if (response.body().isEmpty()){

                        new MaterialDialog.Builder(MapActivity.this)
                                .title("No data found")
                                .content("Sorry, We can not find relavent data in our server!.")
                                .positiveText("Ok")
                                .positiveColor(ContextCompat.getColor(MapActivity.this, R.color.material_green))
                                .build().show();
                        progressDialog.cancel();
                        mGoogleMap.clear();

                    }else{

                        kioskModelList.clear();
                        kioskModelList.addAll(response.body());

                        showMarkers();

                        progressDialog.cancel();
                    }

                }else{

                    Toast.makeText(MapActivity.this, "Something Wrong, Try again later!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<KioskModel>> call, Throwable t) {
                Toast.makeText(MapActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
