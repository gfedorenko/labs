package com.example.galya.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private static final int RC_LOCATION_PERMISSION = 844;

    private static final long SPEED_UPDATE = 200;
    private static final long DISTANCE_UPDATE = 3000;

    private TextView speedTextView;
    private TextView distanceTextView;
    private Button distanceButton;

    private Location lastLocation;
    private long lastTime;
    private long lastTimeDistance;
    private boolean distanceEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedTextView = findViewById(R.id.mainSpeedTextView);
        distanceTextView = findViewById(R.id.mainDistanceTextView);
        distanceButton = findViewById(R.id.mainDistanceButton);
        distanceButton.setOnClickListener(this);

        speedTextView.setText(getString(R.string.speed, 0.0));
        distanceButton.setText(R.string.distance_button_stop);

        setupLocationUpdates();
    }

    private void setupLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_LOCATION_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        long time = System.currentTimeMillis();
        if (lastLocation == null) lastLocation = location;
        if (time - lastTime < SPEED_UPDATE) return; // For accuracy
        lastTime = time;

        Speed.getInstance().addNewPoint(System.currentTimeMillis(), location);

        double speed = Speed.getInstance().getSpeed();
        double speedKMH = speed * 3.6;

        speedTextView.setText(getString(R.string.speed, speedKMH));

        Log.i("MainActivity", "onLocationChanged: " + speed  + " " + (speed) + " " + location);

        if (distanceEnabled && time - lastTimeDistance >= DISTANCE_UPDATE) {
            lastTimeDistance = time;
            double distance = Speed.getInstance().updateDistance();
            String distanceStr;
            if (distance > 1000) distanceStr = getString(R.string.distance_km, distance / 1000);
            else distanceStr = getString(R.string.distance_m, distance);
            distanceTextView.setText(distanceStr);
            Log.i("MainActivity", "onLocationChanged: distance: " + distance);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        distanceEnabled = !distanceEnabled;
        if (distanceEnabled) Speed.getInstance().resetDistance();
        distanceButton.setText(distanceEnabled ? R.string.distance_button_stop : R.string.distance_button_start);
        distanceTextView.setVisibility(distanceEnabled ? View.VISIBLE : View.INVISIBLE);
        distanceTextView.setText(getString(R.string.distance_m, 0.0));
    }
}
