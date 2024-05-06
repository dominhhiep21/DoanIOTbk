package com.example.doaniotbk.Model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id,image;
    private int time;
    private int temperature;
    private float humidity;

    public Item() {
    }

    public Item(int id, int image, int time, int temperature, float humidity) {
        this.id = id;
        this.image = image;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Item(int image, int time, int temperature, float humidity) {
        this.image = image;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
