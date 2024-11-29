package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Fruit {
    private long id;
    private String name;
    private int price;
    private List<Seller> sellers;
    private String seller;

    public Fruit() {
    }

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }


    public Fruit(String name) {
        this.name = name;
    }

    public Fruit(long id, int price) {
        this.id = id;
        this.price = price;
    }

    public Fruit(String name, String seller) {
        this.name = name;
        this.seller = seller;
    }

    public Fruit(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Fruit(long id, String name, int price, List<Seller> sellers) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellers = new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }


}