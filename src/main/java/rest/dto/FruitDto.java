package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class FruitDto {
    private long id;
    private String name;
    private int price;
    private List<SellersDto> sellers;
    private String seller;

    public FruitDto() {
    }

    public FruitDto(String name, int price) {
        this.name = name;
        this.price = price;
    }


    public FruitDto(String name) {
        this.name = name;
    }

    public FruitDto(long id, int price) {
        this.id = id;
        this.price = price;
    }

    public FruitDto(String name, String seller) {
        this.name = name;
        this.seller = seller;
    }

    public FruitDto(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public FruitDto(long id, String name, int price, List<SellersDto> sellers) {
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