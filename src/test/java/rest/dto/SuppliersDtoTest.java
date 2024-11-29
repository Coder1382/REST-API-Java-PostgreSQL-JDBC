package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDtoTest {
    @Test
    public void sellersDtoTest() {
        SuppliersDto suppliersDto = new SuppliersDto();
        assertEquals(suppliersDto.getId(), 0);
        suppliersDto = new SuppliersDto("big");
        assertEquals(suppliersDto.getName(), "big");
        suppliersDto = new SuppliersDto(1, "big");
        assertEquals(suppliersDto.getId(), 1);
        suppliersDto = new SuppliersDto("big", "one");
        assertEquals(suppliersDto.getSeller(), "one");
        List<SellersDto> list = new ArrayList<>();
        list.add(new SellersDto());
        suppliersDto = new SuppliersDto(1, "supplier", list);
        assertEquals(suppliersDto.getName(), "supplier");
    }
}
