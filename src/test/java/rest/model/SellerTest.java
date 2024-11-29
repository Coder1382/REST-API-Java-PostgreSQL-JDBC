package rest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerTest {
    @Test
    public void sellerTest() {
        Seller seller = new Seller();
        assertEquals(seller.getId(), 0);
        seller = new Seller("one");
        assertEquals(seller.getName(), "one");
        seller = new Seller("one", "big");
        assertEquals(seller.getName(), "one");
        List<Fruit> flist = new ArrayList<>();
        Supplier supply = new Supplier();
        seller = new Seller(1, "one", supply);
        assertEquals(seller.getName(), "one");
        seller = new Seller(1, "one", supply, flist);
        assertEquals(seller.getName(), "one");
        seller = new Seller("mango", "one");
        assertEquals(seller.getName(), "mango");
        seller = new Seller("one", "big");
        assertEquals(seller.getSupplier(), "big");
        seller = new Seller("mango", supply);
        assertEquals(seller.getName(), "mango");
        seller = new Seller("one", supply, flist);
        assertEquals(seller.getName(), "one");
        seller = new Seller(1, "fruit");
        assertEquals(seller.getFruit(), "fruit");
        List<Fruit> list = new ArrayList<>();
        list.add(new Fruit());
        seller = new Seller(1, "seller", new Supplier(), list);
        assertEquals(seller.getName(), "seller");
        seller = new Seller("one", 1);
        assertEquals(seller.getName(), "one");
        seller = new Seller("one", "fruit", "mango");
        assertEquals(seller.getValue(), "mango");
        assertEquals(seller.getField(), "fruit");
    }
}
