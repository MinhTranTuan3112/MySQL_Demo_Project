package com.example.mysql_demo_project;

public class Product {
    public int id;
    public String name;
    public double price;

    public Product() {

    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
