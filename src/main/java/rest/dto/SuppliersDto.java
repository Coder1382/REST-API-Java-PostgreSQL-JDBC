package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class SuppliersDto {
    private long id = 0;
    private String name = "";
    private List<SellersDto> sellers = new ArrayList<>();
    private String seller = "";

    public SuppliersDto() {
    }

    public SuppliersDto(String name) {
        this.name = name;
    }

    public SuppliersDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SuppliersDto(long id, String name, List<SellersDto> sellers) {
        this.id = id;
        this.name = name;
        this.sellers = new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
    }

    public SuppliersDto(String name, String seller) {
        this.name = name;
        this.seller = seller;
    }
    public SuppliersDto(String seller, long id) {
        this.id = id;
        this.seller = seller;
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
