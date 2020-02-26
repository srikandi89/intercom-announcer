package com.intercom.announcer.utilities;

import com.intercom.announcer.entities.Location;

public class LocationUtility {

    // Earth radius in kilometers
    private static final double R = 6371;

    public static double getDistance(Location l1, Location l2) {
        double lat1 = Math.toRadians(l1.getLatitude());
        double lat2 = Math.toRadians(l2.getLatitude());

        double dLat = Math.toRadians(l2.getLatitude()-l1.getLatitude());
        double dLng = Math.toRadians(Math.abs(l2.getLongitude() - l1.getLongitude()));

        double a = hav(dLat) + (Math.cos(lat1) * Math.cos(lat2) * hav(dLng));
        double centralAngle = 2 * Math.asin(Math.sqrt(a));

        return centralAngle * R;
    }

    private static double hav(double deg) {
        return Math.pow(Math.sin(deg/2), 2);
    }
}
