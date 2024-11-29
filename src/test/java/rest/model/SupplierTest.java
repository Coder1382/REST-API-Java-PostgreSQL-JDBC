package rest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierTest {
    @Test
    public void supplierTest() {
        Supplier supplier = new Supplier();
        assertEquals(supplier.getId(), 0);
        supplier = new Supplier("big");
        assertEquals(supplier.getName(), "big");
        supplier = new Supplier(1, "big");
        assertEquals(supplier.getId(), 1);
        supplier = new Supplier("big", "one");
        assertEquals(supplier.getSeller(), "one");
        List<Seller> list = new ArrayList<>();
        list.add(new Seller());
        supplier = new Supplier(1, "supplier", list);
        assertEquals(supplier.getName(), "supplier");
    }
}
