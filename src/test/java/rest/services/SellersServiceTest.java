package rest.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersServiceTest {
    SellersService sellersService = new SellersService();
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
        assertEquals((sellersService.find().get(0)).getName(), "one");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(sellersService.find(1).getName(), "one");
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(sellersService.save(new SellersDto("four", "big")), 4);
        assertEquals(sellersService.save(new SellersDto("five", "")), 5);
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        assertEquals(sellersService.update(new SellersDto("five", "supplier", "small")), 5);
        assertEquals(sellersService.update(new SellersDto("four", "fruit", "mango")), 4);
    }

   @Test
    public void deleteTest() throws IOException {
        assertEquals(sellersService.delete(new SellersDto("four")), 4);
    }
}
