package com.example.app01;

public class Data {
    int id,quantity;
    String name;
    float price;

    public Data(int id, String name , int quantity, float price) {
        this.id = id;
        this.quantity =quantity;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
