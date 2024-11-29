package rest.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.dto.SuppliersDto;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersServiceTest {
    SuppliersService suppliersService = new SuppliersService();
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
        assertEquals((suppliersService.find().get(0)).getName(), "big");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(suppliersService.find(1).getName(), "big");
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(suppliersService.save(new SuppliersDto("huge")), 4);
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        assertEquals(suppliersService.update(new SuppliersDto("huge", "two")), 4);
    }

    @Test
    public void deleteTest() throws IOException {
        assertEquals(suppliersService.delete(new SuppliersDto("huge")), 4);
    }
}
