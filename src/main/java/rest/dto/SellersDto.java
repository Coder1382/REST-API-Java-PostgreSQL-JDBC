package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class SellersDto {
    private long id;
    private String name;
    private String supply;
    private String fruit;
    private String value;
    private String field;
    private SuppliersDto supplier;
    private List<FruitDto> fruits;

    public SellersDto(){}

    public SellersDto(String name) {
        this.name=name;
    }

    public SellersDto(long id, String fruit) {
        this.id = id;
        this.fruit = fruit;
    }

    public SellersDto(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public SellersDto(String name, String field, String value) {
        this.name = name;
        this.field=field;
        this.value=value;
    }
    public SellersDto(String name, String supply) {
        this.name=name;
        this.supply=supply;
    }
    public SellersDto(String name, SuppliersDto supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public SellersDto(String name, SuppliersDto supplier, List<FruitDto> fruits) {
        this.name = name;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public SellersDto(long id, String name, SuppliersDto supplier) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
    }
    public SellersDto(String name, long id, List<FruitDto> fruits) {
        this.id = id;
        this.name = name;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }
    public SellersDto(long id, String name, SuppliersDto supplier, List<FruitDto> fruits) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public long getId() {
        return id;
    }

    public String getSupplier() {
        return supply;
    }

    public String getName() {
        return name;
    }

    public String getFruit() {
        return fruit;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}