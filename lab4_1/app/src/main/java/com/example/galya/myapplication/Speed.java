package com.example.galya.myapplication;

import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Speed {

    private static final int PREVIOUS_POINTS = 10;

    private static Speed speed;
    private List<Long> times;
    private List<Location> points;


    private Location lastDistanceMeanLocation;
    private double distance;

    public static Speed getInstance() {
        if (speed == null) speed = new Speed();
        return speed;
    }

    private Speed() {
        times = new LinkedList<>();
        points = new LinkedList<>();
    }

    public void addNewPoint(long time, @NonNull Location location) {
        points.add(0, location);
        times.add(0, time);

        int size = points.size();
        if (size > PREVIOUS_POINTS + 1) {
            points.remove(size - 1);
            times.remove(size - 1);
        }
    }


    /*
        Function for getting the speed
        Calculate separately speed by X and Y coordinate
        Use each previous point as the last point to get more accurate average number

     */

    public double getSpeed() {
        if (points.size() < PREVIOUS_POINTS + 1) return 0;

        double[] diffLatitudeSpeed = new double[PREVIOUS_POINTS];
        double[] diffLongitudeSpeed = new double[PREVIOUS_POINTS];

        Iterator<Location> locationIterator = points.iterator();
        Iterator<Long> timeIterator = times.iterator();
        Location lastLocation = locationIterator.next();
        long lastTime = timeIterator.next();

        int i = 0;
        while (locationIterator.hasNext() && i < PREVIOUS_POINTS) {
            Location location = locationIterator.next();

            double diffTime = lastTime - timeIterator.next();
            double diffLatitude = lastLocation.getLatitude() - location.getLatitude();
            double diffLongitude = lastLocation.getLongitude() - location.getLongitude();

            double averageLongitude = (location.getLongitude() + lastLocation.getLongitude()) / 2;
            double lenLatitude = Math.cos(averageLongitude) * 40075696;

            double diffLatitudeMeters = lenLatitude * diffLatitude / 360;
            double diffLongitudeMeters = 20004274 * diffLongitude / 180;

            diffLatitudeSpeed[i] = diffLatitudeMeters / diffTime * 1000;
            diffLongitudeSpeed[i] = diffLongitudeMeters / diffTime * 1000;

            i++;
        }

        double diffLatitudeSpeedMean = 0;
        double diffLongitudeSpeedMean = 0;
        for (i = 0; i < PREVIOUS_POINTS; i++) {
            diffLatitudeSpeedMean += diffLatitudeSpeed[i];
            diffLongitudeSpeedMean += diffLongitudeSpeed[i];
        }
        diffLatitudeSpeedMean /= PREVIOUS_POINTS;
        diffLongitudeSpeedMean /= PREVIOUS_POINTS;

        return Math.hypot(diffLatitudeSpeedMean, diffLongitudeSpeedMean);
    }

    public void resetDistance() {
        distance = 0;
        lastDistanceMeanLocation = getMeanLocation();
    }

    /*
        Function fot getting distance
        Calculate distance between last and current average points
        Adding current distance to the general
     */
    public double updateDistance() {
        if (lastDistanceMeanLocation == null) {
            lastDistanceMeanLocation = getMeanLocation();
            return distance;
        }

        Location location = getMeanLocation();
        if (location == null) return distance;

        double diffLatitude = Math.abs(lastDistanceMeanLocation.getLatitude() - location.getLatitude());
        double diffLongitude = Math.abs(lastDistanceMeanLocation.getLongitude() - location.getLongitude());

        double averageLongitude = (location.getLongitude() + lastDistanceMeanLocation.getLongitude()) / 2;
        double lenLatitude = Math.cos(averageLongitude) * 40075696;

        double diffLatitudeMeters = lenLatitude * diffLatitude / 360;
        double diffLongitudeMeters = 20004274 * diffLongitude / 180;

        lastDistanceMeanLocation = location;

        if (diffLatitudeMeters < 100 || diffLongitudeMeters < 100) {
            diffLatitudeMeters *= 0.5;
            diffLongitudeMeters *= 0.5;
        }
        distance += Math.hypot(diffLatitudeMeters, diffLongitudeMeters);
        return distance;
    }

    /*
        Function for getting current average location
        Get average location by X and Y separately
     */
    private Location getMeanLocation() {
        if (points.size() < PREVIOUS_POINTS + 1) return null;
        double latitude = 0;
        double longitude = 0;
        int count = 0;
        for (Location location : points) {
            latitude += location.getLatitude();
            longitude += location.getLongitude();
            count++;
        }

        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latitude / count);
        location.setLongitude(longitude / count);
        return location;
    }
}
