package rest.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();
    static DatabaseTest dbt = new DatabaseTest();
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    public static void beforeAll() throws SQLException {
        postgres.start();
        dbt.TablesTest();
    }
    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @Test
    public void findTest_1() throws IOException {
        assertEquals((fruitService.find().get(0)).getPrice(), 10);
        assertEquals((fruitService.find().get(0)).getName(), "mango");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(fruitService.find(1).getPrice(), 10);
        assertEquals(fruitService.find(1).getName(), "mango");
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(fruitService.save(new FruitDto("tomato", 30)), 4);
    }

    @Test
    public void updateTest_1() throws SQLException, IOException {
        assertEquals(fruitService.update(new FruitDto("tomato", 20)), 4);
    }

    @Test
    public void updateTest_2() throws SQLException, IOException {
        assertEquals(fruitService.update(new FruitDto("tomato", "one")), 4);
    }

    @Test
    public void deleteTest() throws IOException {
        assertEquals(fruitService.delete(new FruitDto("tomato")), 4);
    }
}
