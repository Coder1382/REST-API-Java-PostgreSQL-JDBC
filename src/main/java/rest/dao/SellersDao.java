package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;
import rest.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellersDao {
 public long save(Seller seller) {
	String name = seller.getName();
	String supplier = seller.getSupplier();
	long id = 0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement addSeller = connect.prepareStatement("INSERT INTO sellers(name, supplier) VALUES(?,?)")) {
	 addSeller.setString(1, name);
	 addSeller.setString(2, supplier);
	 addSeller.executeUpdate();
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=?")){
	 readSellers.setString(1, name);
	 ResultSet result = readSellers.executeQuery();
	 if(result.next())
		id = result.getLong("id");
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSuppliers = connect.prepareStatement("UPDATE suppliers SET sellers=array_append(sellers,?) WHERE name=?")){
	 updateSuppliers.setString(1, name);
	 updateSuppliers.setString(2, supplier);
	 updateSuppliers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public Seller find(long id) {
	Seller sellers = new Seller();
	long id_supplier=0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSellers = connect.prepareStatement("SELECT name, supplier, fruits FROM sellers WHERE id=?")) {
	 readSellers.setLong(1, id);
	 ResultSet result = readSellers.executeQuery();
	 if (result.next()) {
		String name = result.getString("name");
		String supplier = result.getString("supplier");
		Array arr = result.getArray("fruits");
		try(PreparedStatement readSuppliers = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?")) {
		 readSuppliers.setString(1, supplier);
		 ResultSet rs = readSuppliers.executeQuery();
		 if (rs.next())
			id_supplier = rs.getLong("id");
		}catch (SQLException e) {
		 throw new RuntimeException(e);
		}
		if (arr != null) {
		 String[] fruits = (String[]) arr.getArray();
		 if(fruits.length>0) {
			List<Fruit> fruitList = new ArrayList<>();
			for (int k = 0; k < fruits.length; ++k) {
			 try (PreparedStatement readFruit = connect.prepareStatement("SELECT id, price FROM fruit WHERE name=?")) {
				readFruit.setString(1, fruits[k]);
				ResultSet rs = readFruit.executeQuery();
				if (rs.next())
				 fruitList.add(new Fruit(rs.getLong("id"), fruits[k], rs.getInt("price")));
			 } catch (SQLException e) {
				throw new RuntimeException(e);
			 }
			}
			if (supplier != null && !supplier.isEmpty())
			 sellers = new Seller(id, name, new Supplier(id_supplier, supplier), fruitList);
			else
			 sellers = new Seller(name, id, fruitList);
		 }
		 else {
			if (supplier != null && !supplier.isEmpty())
			 sellers = new Seller(id, name, new Supplier(id_supplier, supplier));
			else
			 sellers = new Seller(name, id);
		 }
		}
		else {
		 if (supplier != null && !supplier.isEmpty())
			sellers = new Seller(id, name, new Supplier(id_supplier, supplier));
		 else
			sellers = new Seller(name, id);
		}
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return sellers;
 }

 public List<Seller> find() {
	List<Seller> sellers = new ArrayList<>();
	long id_supplier=0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readDB = connect.prepareStatement("SELECT id, name, supplier, fruits FROM sellers")) {
	 ResultSet result = readDB.executeQuery();
	 while (result.next()) {
		long i = result.getLong("id");
		String name = result.getString("name");
		String supplier = result.getString("supplier");
		Array arr = result.getArray("fruits");
		try(PreparedStatement rdb = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?")) {
		 rdb.setString(1, supplier);
		 ResultSet rs = rdb.executeQuery();
		 if (rs.next())
			id_supplier = rs.getLong("id");
		}catch (SQLException e) {
		 throw new RuntimeException(e);
		}
		if (arr != null) {
		 String[] fruits = (String[]) arr.getArray();
		 if(fruits.length>0) {
			List<Fruit> fruitList = new ArrayList<>();
			for (int k = 0; k < fruits.length; ++k) {
			 try (PreparedStatement readFruit = connect.prepareStatement("SELECT id, price FROM fruit WHERE name=?")) {
				readFruit.setString(1, fruits[k]);
				ResultSet rs = readFruit.executeQuery();
				if (rs.next())
				 fruitList.add(new Fruit(rs.getLong("id"), fruits[k], rs.getInt("price")));
			 } catch (SQLException e) {
				throw new RuntimeException(e);
			 }
			}
			if (supplier != null && !supplier.isEmpty())
			 sellers.add(new Seller(i, name, new Supplier(id_supplier, supplier), fruitList));
			else sellers.add(new Seller(name, i, fruitList));
		 }
		 else{
			if(supplier!=null && !supplier.isEmpty())
			 sellers.add(new Seller(i, name, new Supplier(id_supplier, supplier)));
			else sellers.add(new Seller(name, i));
		 }
		}
		else{
		 if(supplier!=null && !supplier.isEmpty())
			sellers.add(new Seller(i, name, new Supplier(id_supplier, supplier)));
		 else sellers.add(new Seller(name, i));
		}
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return sellers;
 }
 public long updateFruit(Seller seller) throws SQLException {
	String name = seller.getName();
	String fruit = seller.getValue();
	long id=0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and ?=ANY(fruits)")) {
	 readSellers.setString(1, name);
	 readSellers.setString(2, fruit);
	 ResultSet rs = readSellers.executeQuery();
	 if (rs.next()) {
		id=rs.getLong("id");
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET fruits=array_append(fruits,?) WHERE name=?")){
	 updateSellers.setString(1, fruit);
	 updateSellers.setString(2, name);
	 updateSellers.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and ?=ANY(fruits)")) {
	 readSellers.setString(1, name);
	 readSellers.setString(2, fruit);
	 ResultSet rs = readSellers.executeQuery();
	 if(rs.next())
		id=rs.getLong("id");
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and ?=ANY(sellers)")) {
	 readFruit.setString(1, fruit);
	 readFruit.setString(2, name);
	 ResultSet rs = readFruit.executeQuery();
	 if (rs.next()) {
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE fruit SET sellers=array_append(sellers,?) WHERE name=?")){
	 updateSellers.setString(1, name);
	 updateSellers.setString(2, fruit);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }
 public long updateSupplier(Seller seller) throws SQLException {
	String name = seller.getName();
	String supplier = seller.getValue();
	long id=0;
	Connection connect = DatabaseConnector.connector();
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET supplier=? WHERE name=?")){
	 updateSellers.setString(1, supplier);
	 updateSellers.setString(2, name);
	 updateSellers.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and supplier=?")) {
	 readSellers.setString(1, name);
	 readSellers.setString(2, supplier);
	 ResultSet rs = readSellers.executeQuery();
	 if(rs.next())
		id=rs.getLong("id");
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM suppliers WHERE name=? and ?=ANY(sellers)")) {
	 readFruit.setString(1, supplier);
	 readFruit.setString(2, name);
	 ResultSet rs = readFruit.executeQuery();
	 if (rs.next()) {
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE suppliers SET sellers=array_append(sellers,?) WHERE name=?")){
	 updateSellers.setString(1, name);
	 updateSellers.setString(2, supplier);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }
 public long delete(Seller seller) {
	String name = seller.getName();
	long id=0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=?")) {
	 readSellers.setString(1, name);
	 ResultSet rs= readSellers.executeQuery();
	 if(rs.next())
		id=rs.getLong("id");
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement deleteSeller = connect.prepareStatement("DELETE FROM sellers WHERE name=?")) {
	 deleteSeller.setString(1, name);
	 deleteSeller.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement updateSuppliers = connect.prepareStatement("UPDATE suppliers SET sellers=array_remove(sellers,?)")) {
	 updateSuppliers.setString(1, name);
	 updateSuppliers.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement updateFruit = connect.prepareStatement("UPDATE fruit SET sellers=array_remove(sellers,?)")) {
	 updateFruit.setString(1, name);
	 updateFruit.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }
}
