package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.dto.SuppliersDto;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class SuppliersServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    SuppliersServlet suppliersServlet = new SuppliersServlet();
    SuppliersService suppliersService = mock(SuppliersService.class);
    Gson jsn = mock(Gson.class);
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
    public void findTest_1() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(res.getWriter()).thenReturn(pw);
        suppliersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"big\"}\n\n");
    }

    @Test
    public void findTest_3() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/1");
        when(res.getWriter()).thenReturn(pw);
        suppliersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"big\"}");
    }

    @Test
    public void saveTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto("huge");
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(suppliersService.save(suppliersDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doPost(req, res);
            verify(pw).write("saved with id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_1() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SuppliersDto suppliersDto = new SuppliersDto("big", "one");
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doPut(req, res);
            verify(pw).write("updated under id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_2() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SuppliersDto suppliersDto = new SuppliersDto("big", "two");
        when(req.getRequestURI()).thenReturn("/myREST/supplier");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        doThrow(RuntimeException.class).when(ex);
        try {
            suppliersServlet.doPut(req, res);
            verify(doThrow(RuntimeException.class).when(ex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto("big");
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doDelete(req, res);
            verify(pw).write("deleted under id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
