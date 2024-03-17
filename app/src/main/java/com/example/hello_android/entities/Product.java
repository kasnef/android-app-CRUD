package com.example.hello_android.entities;

import androidx.annotation.NonNull;

public class Product {
    private String name;
    private double price;

    private String image;

    public Product(String name, double price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return  "name: " + name + '\n' +
                "price: " + price + '\n' +
                "image: " + image + '\n';
    }
}
