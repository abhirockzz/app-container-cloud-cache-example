package com.oracle.cloud.acc.cache.dcs;

public class Ticker {

    private String name;
    private String price;
    private String time;

    public Ticker() {
    }

    public Ticker(String name, String price, String time) {
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Ticker{" + "name=" + name + ", price=" + price + ", time=" + time + '}';
    }

}
