package rest.services;

import rest.dao.SuppliersDao;
import rest.dto.SuppliersDto;

import rest.model.Supplier;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;

public class SuppliersService {
    private final SuppliersDao suppliersDao = new SuppliersDao();

    public List<Supplier> find() throws IOException {
        return suppliersDao.find();
    }

    public Supplier find(long id) throws IOException {
        return suppliersDao.find(id);
    }

    public long save(SuppliersDto suppliersDto) throws IOException {
        Supplier supplier = new Supplier(suppliersDto.getName());
        return suppliersDao.save(supplier);
    }

    public long update(SuppliersDto suppliersDto) throws IOException, SQLException {
        Supplier supplier = new Supplier(suppliersDto.getName(), suppliersDto.getSeller());
        return suppliersDao.update(supplier);
    }

    public long delete(SuppliersDto suppliersDto) throws IOException {
        Supplier supplier = new Supplier(suppliersDto.getName());
        return suppliersDao.delete(supplier);
    }
}
