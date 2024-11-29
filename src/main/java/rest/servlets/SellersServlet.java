package rest.servlets;//package servlets;

import com.google.gson.Gson;
import rest.dto.SellersDto;
import rest.services.SellersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

public class SellersServlet extends HttpServlet {
    private final SellersService sellersService = new SellersService();
    private final Gson jsn = new Gson();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("sellers")) {
            PrintWriter pw = res.getWriter();
            pw.write("saved with id: " + sellersService.save(jsn.fromJson(req.getReader(), SellersDto.class)));
            pw.close();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri.length < 5 && uri[2].equals("sellers")) {
            long id = -1;
            if (uri.length == 4 && !uri[3].equals("all"))
                try {
                    id = Long.parseLong(uri[3]);
                } catch (NumberFormatException e) {
                    return;
                }
            PrintWriter pw = res.getWriter();
            if (id == -1)
                sellersService.find().forEach(e -> {
                    pw.write(jsn.toJson(e) + "\n\n");
                });
            else pw.write(jsn.toJson(sellersService.find(id)));
            pw.close();
        } else return;
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("sellers")) {
            PrintWriter pw = res.getWriter();
            try {
                pw.write("updated under id: " + sellersService.update(jsn.fromJson(req.getReader(), SellersDto.class)));
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }
            pw.close();
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().split("/")[2].equals("sellers")) {
            PrintWriter pw = res.getWriter();
            pw.write("deleted under id: " + sellersService.delete(jsn.fromJson(req.getReader(), SellersDto.class)));
            pw.close();
        }
    }
}