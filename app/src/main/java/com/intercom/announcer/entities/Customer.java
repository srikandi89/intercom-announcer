package com.intercom.announcer.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Here are the fields required to be mapped to customer object
 * latitude: Double,
 * user_id: Long,
 * name: String,
 * longitude: Double
 * distance (derived from haversine distance between 2 location)
 *
 * The properties above will be automatically mapped to camel case convention
 */
public class Customer {
    private int userId;
    private String name;
    private double latitude;
    private double longitude;
    private double distance;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static Customer toCustomer(String raw) {

        try {
            JsonParser parser       = new JsonParser();
            JsonElement jsonTree    = parser.parse(raw);
            JsonObject object       = jsonTree.getAsJsonObject();
            Customer customer       = new Customer();

            customer.setLatitude(object.get("latitude").getAsDouble());
            customer.setLongitude(object.get("longitude").getAsDouble());
            customer.setName(object.get("name").getAsString());
            customer.setUserId(object.get("user_id").getAsInt());

            return customer;
        }
        catch (JsonParseException e) {
            return null;
        }
    }
}
