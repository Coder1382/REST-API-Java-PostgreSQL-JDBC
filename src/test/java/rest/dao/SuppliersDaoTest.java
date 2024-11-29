package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Supplier;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDaoTest {
    SuppliersDao suppliersDao = new SuppliersDao();
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
    public void findTest_1() throws IOException {
        assertEquals((suppliersDao.find().get(0)).getName(), "big");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(suppliersDao.find(1).getName(), "big");
    }

    @Test
    public void saveTest() {
        assertEquals(suppliersDao.save(new Supplier("huge")), 4);
    }


    @Test
    public void updateTest() throws SQLException {
        assertEquals(suppliersDao.update(new Supplier("huge", "two")), 4);
    }

    @Test
    public void findTest_3() throws IOException {
        assertEquals((suppliersDao.find().get(3)).getName(), "huge");
    }

    @Test
    public void findTest_4() throws IOException {
        assertEquals(suppliersDao.find(4).getName(), "huge");
    }

    @Test
    public void deleteTest() {
        assertEquals(suppliersDao.delete(new Supplier("huge")), 4);
    }

}
