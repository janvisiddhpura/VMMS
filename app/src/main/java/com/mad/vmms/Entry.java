package com.mad.vmms;

public class Entry {

    private int id;
    private float volume;
    private float price;
    private float odometer;
    private String date;
    private String avg;
    private String rs;

    public Entry(int id, String date, float volume, float price, float odometer, String avg, String rs) {
        this.id = id;
        this.volume = volume;
        this.price = price;
        this.odometer = odometer;
        this.date = date;
        this.avg = avg;
        this.rs = rs;
    }

    public int getId() {
        return id;
    }

    public float getVolume() {
        return volume;
    }

    public float getPrice() {
        return price;
    }

    public float getOdometer() {
        return odometer;
    }

    public String getDate() {
        return date;
    }

    public String getAvg() {
        return avg;
    }

    public String getRs() {
        return rs;
    }
}
