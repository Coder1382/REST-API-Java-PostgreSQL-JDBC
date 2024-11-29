package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersDtoTest {
    @Test
    public void sellersDtoTest() {
        SellersDto sellersDto = new SellersDto();
        assertEquals(sellersDto.getId(), 0);
        sellersDto = new SellersDto("one");
        assertEquals(sellersDto.getName(), "one");
        sellersDto = new SellersDto("one", "big");
        assertEquals(sellersDto.getName(), "one");
        List<FruitDto> flist = new ArrayList<>();
        SuppliersDto supply = new SuppliersDto();
        sellersDto = new SellersDto(1, "one", supply);
        assertEquals(sellersDto.getName(), "one");
        sellersDto = new SellersDto(1, "one", supply, flist);
        assertEquals(sellersDto.getName(), "one");
        sellersDto = new SellersDto("mango", "one");
        assertEquals(sellersDto.getName(), "mango");
        sellersDto = new SellersDto("one", "big");
        assertEquals(sellersDto.getSupplier(), "big");
        sellersDto = new SellersDto("mango", supply);
        assertEquals(sellersDto.getName(), "mango");
        sellersDto = new SellersDto("one", supply, flist);
        assertEquals(sellersDto.getName(), "one");
        sellersDto = new SellersDto(1, "fruit");
        assertEquals(sellersDto.getFruit(), "fruit");
        List<FruitDto> list = new ArrayList<>();
        list.add(new FruitDto());
        sellersDto = new SellersDto(1, "seller", new SuppliersDto(), list);
        assertEquals(sellersDto.getName(), "seller");
        sellersDto = new SellersDto("one", 1);
        assertEquals(sellersDto.getName(), "one");
        sellersDto = new SellersDto("one", "fruit", "mango");
        assertEquals(sellersDto.getValue(), "mango");
        assertEquals(sellersDto.getField(), "fruit");
    }
}
