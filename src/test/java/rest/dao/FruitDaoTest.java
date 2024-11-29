package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Fruit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

public class FruitDaoTest {
    FruitDao fruitDao = new FruitDao();
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
        assertEquals((fruitDao.find().get(0)).getName(), "mango");
        assertEquals((fruitDao.find().get(0)).getPrice(), 10);
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(fruitDao.find(1).getName(), "mango");
        assertEquals(fruitDao.find(1).getPrice(), 10);
    }

    @Test
    public void saveTest() {
        assertEquals(fruitDao.save(new Fruit("tomato", 30)), 4);
    }

    @Test
    public void updateTest_1() {
        assertEquals(fruitDao.updatePrice(new Fruit("tomato", 20)), 4);
    }

    @Test
    public void updateTest_2() {
        assertEquals(fruitDao.updateSellers(new Fruit("tomato", "one")), 4);
    }
    @Test
    public void findTest_3() throws IOException {
        assertEquals((fruitDao.find().get(3)).getName(), "tomato");
        assertEquals((fruitDao.find().get(3)).getPrice(), 20);
    }
    @Test
    public void findTest_4() throws IOException {
        assertEquals(fruitDao.find(4).getName(), "tomato");
        assertEquals(fruitDao.find(4).getPrice(), 20);
    }
    @Test
    public void deleteTest() {
        assertEquals(fruitDao.delete(new Fruit("tomato")), 4);
    }
}
