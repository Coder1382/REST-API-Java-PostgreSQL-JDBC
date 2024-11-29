package rest.servlets;

import com.google.gson.Gson;
import rest.dto.SuppliersDto;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

public class SuppliersServlet extends HttpServlet {
    private final SuppliersService suppliersService = new SuppliersService();
    private final Gson jsn = new Gson();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            pw.write("saved with id: " + suppliersService.save(jsn.fromJson(req.getReader(), SuppliersDto.class)));
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri.length < 5 && uri[2].equals("suppliers")) {
            long id = -1;
            if (uri.length == 4 && !uri[3].equals("all"))
                try {
                    id = Long.parseLong(uri[3]);
                } catch (NumberFormatException e) {
                    return;
                }
            PrintWriter pw = res.getWriter();
            if (id == -1)
                suppliersService.find().forEach(e -> {
                    pw.write(jsn.toJson(e) + "\n\n");
                });
            else pw.write(jsn.toJson(suppliersService.find(id)));
            pw.close();
        } else return;
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            try {
                pw.write("updated under id: " + suppliersService.update(jsn.fromJson(req.getReader(), SuppliersDto.class)));
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }
            pw.close();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("suppliers")) {
            PrintWriter pw = res.getWriter();
            pw.write("deleted under id: " + suppliersService.delete(jsn.fromJson(req.getReader(), SuppliersDto.class)));
            pw.close();
        }
    }
}