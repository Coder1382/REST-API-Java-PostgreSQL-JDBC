package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitDtoTest {
    @Test
    public void fruitDtoTest() {
        FruitDto fruitDto = new FruitDto();
        assertEquals(fruitDto.getId(), 0);
        fruitDto = new FruitDto("mango");
        assertEquals(fruitDto.getName(), "mango");
        fruitDto = new FruitDto("tomato", 10);
        assertEquals(fruitDto.getPrice(), 10);
        fruitDto = new FruitDto(1, "tomato", 10);
        assertEquals(fruitDto.getId(), 1);
        fruitDto = new FruitDto("mango", 5);
        assertEquals(fruitDto.getPrice(), 5);
        List<SellersDto> list = new ArrayList<>();
        list.add(new SellersDto());
        fruitDto = new FruitDto(1, "fruit", 10, list);
        assertEquals(fruitDto.getName(), "fruit");
        fruitDto = new FruitDto(1, 5);
        assertEquals(fruitDto.getPrice(), 5);
    }
}
