package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.dto.FruitDto;
import rest.services.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class FruitServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    FruitServlet fruitServlet = new FruitServlet();
    FruitService fruitService = mock(FruitService.class);
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
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(res.getWriter()).thenReturn(pw);
        fruitServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"mango\",\"price\":10}\n\n");
    }

    @Test
    public void findTest_3() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/fruit/1");
        when(res.getWriter()).thenReturn(pw);
        fruitServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"mango\",\"price\":10}");
    }

    @Test
    public void saveTest() throws IOException {
        FruitDto fruitDto = new FruitDto("tomato", 30);
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(fruitService.save(fruitDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doPost(req, res);
            verify(pw).write("saved with id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_1() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        FruitDto fruitDto = new FruitDto("tomato", 20);
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doPut(req, res);
            verify(pw).write("updated under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_2() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        FruitDto fruitDto = new FruitDto(1, 10);
        when(req.getRequestURI()).thenReturn("/myREST/frui");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(res.getWriter()).thenReturn(pw);
        doThrow(RuntimeException.class).when(ex);
        try {
            fruitServlet.doPut(req, res);
            verify(doThrow(RuntimeException.class).when(ex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() throws IOException {
        FruitDto fruitDto = new FruitDto("mango");
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doDelete(req, res);
            verify(pw).write("deleted under id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
