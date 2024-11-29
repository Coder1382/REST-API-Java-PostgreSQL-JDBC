package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private long id;
    private String name;
    private List<Seller> sellers;
    private String seller;

    public Supplier() {
    }

    public Supplier(long id) {
        this.id = id;
    }

    public Supplier(String name, String seller) {
        this.name = name;
        this.seller = seller;
    }

    public Supplier(String name) {
        this.name = name;
    }

    public Supplier(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Supplier(String seller, long id) {
        this.id = id;
        this.seller = seller;
    }
    public Supplier(long id, String name, List<Seller> sellers) {
        this.id = id;
        this.name = name;
        this.sellers = new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
    }

    public String getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
