package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Seller;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersDaoTest {
    private SellersDao sellersDao = new SellersDao();
    static DatabaseTest dbt = new DatabaseTest();
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    public static void before() throws SQLException {
        postgres.start();
        dbt.TablesTest();
    }

    @AfterAll
    public static void after() {
        postgres.stop();
    }

    @Test
    public void findTest_1() {
        assertEquals((sellersDao.find().get(0)).getName(), "one");
    }

    @Test
    public void findTest_2() {
        assertEquals(sellersDao.find(1).getName(), "one");
    }

    @Test
    public void saveTest() {
        assertEquals(sellersDao.save(new Seller("fourth", "big")), 4);
        assertEquals(sellersDao.save(new Seller("five", "")), 5);
    }

    @Test
    public void updateTest() throws SQLException {
        assertEquals(sellersDao.updateFruit(new Seller("fourth", "fruit", "mango")), 4);
        assertEquals(sellersDao.updateSupplier(new Seller("five", "supplier", "small")), 5);
    }
    @Test
    public void findTest_3() {
        assertEquals(sellersDao.find(4).getName(), "fourth");
    }
    @Test
    public void findTest_4() {
        assertEquals((sellersDao.find().get(4)).getName(), "five");
    }
    @Test
    public void deleteTest() {
        assertEquals(sellersDao.delete(new Seller("fourth")), 4);
        assertEquals(sellersDao.delete(new Seller("five")), 5);
    }

}
