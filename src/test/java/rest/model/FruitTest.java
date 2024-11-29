package rest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitTest {
    @Test
    public void fruitTest() {
        Fruit fruit = new Fruit();
        assertEquals(fruit.getId(), 0);
        fruit = new Fruit("mango");
        assertEquals(fruit.getName(), "mango");
        fruit = new Fruit("tomato", 10);
        assertEquals(fruit.getPrice(), 10);
        fruit = new Fruit(1, "tomato", 10);
        assertEquals(fruit.getId(), 1);
        fruit = new Fruit("mango", 5);
        assertEquals(fruit.getPrice(), 5);
        List<Seller> list = new ArrayList<>();
        list.add(new Seller());
        fruit = new Fruit(1, "fruit", 10, list);
        assertEquals(fruit.getName(), "fruit");
        fruit = new Fruit(1, 5);
        assertEquals(fruit.getPrice(), 5);
    }
}
