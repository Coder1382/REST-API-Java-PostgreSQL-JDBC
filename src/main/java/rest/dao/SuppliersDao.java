package rest.dao;

import rest.model.Seller;
import rest.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao {
 public long save(Supplier supplier) {
	String comp = supplier.getName();
	long id = 0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement addSupplier = connect.prepareStatement("INSERT INTO suppliers (name) VALUES(?)")) {
	 addSupplier.setString(1, comp);
	 addSupplier.executeUpdate();
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement readSuppliers = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?")){
	 readSuppliers.setString(1, comp);
	 ResultSet result = readSuppliers.executeQuery();
	 if (result.next())
		id = result.getLong("id");
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public long delete(Supplier supplier) {
	long id=0;
	String name = supplier.getName();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSuppliers = connect.prepareStatement("SELECT id from suppliers WHERE name=?")) {
	 readSuppliers.setString(1, name);
	 ResultSet result= readSuppliers.executeQuery();
	 if(result.next())
		id=result.getLong("id");
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement deleteSupplier = connect.prepareStatement("DELETE from suppliers WHERE name=?")) {
	 deleteSupplier.setString(1, name);
	 deleteSupplier.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET supplier=? WHERE supplier=?")) {
	 updateSellers.setString(1, null);
	 updateSellers.setString(2, name);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public long update(Supplier supplier) throws SQLException {
	long id=0;
	String name = supplier.getName();
	String seller = supplier.getSeller();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSuppliers = connect.prepareStatement("SELECT id FROM suppliers WHERE name=? and ?=ANY(sellers)")) {
	 readSuppliers.setString(1, name);
	 readSuppliers.setString(2, seller);
	 ResultSet result = readSuppliers.executeQuery();
	 if (result.next()) {
		id= result.getLong("id");
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSuppliers = connect.prepareStatement("UPDATE suppliers SET sellers=array_append(sellers,?) WHERE name=?")) {
	 updateSuppliers.setString(1, seller);
	 updateSuppliers.setString(2, name);
	 updateSuppliers.executeUpdate();
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement readSuppliersAgain = connect.prepareStatement("SELECT id FROM suppliers WHERE name=?")) {
	 readSuppliersAgain.setString(1, name);
	 ResultSet result = readSuppliersAgain.executeQuery();
	 if(result.next())
		id = result.getLong("id");
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET supplier=? WHERE name=?")){
	 updateSellers.setString(1, name);
	 updateSellers.setString(2, seller);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public Supplier find(long id){
	Supplier suppliers = new Supplier();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSuppliers = connect.prepareStatement("SELECT name, sellers FROM suppliers WHERE id=?")) {
	 readSuppliers.setLong(1, id);
	 ResultSet result = readSuppliers.executeQuery();
	 if (result.next()) {
		String name = result.getString("name");
		Array arr = result.getArray("sellers");
		if (arr != null) {
		 String[] sellers = (String[]) arr.getArray();
		 if(sellers.length>0) {
			List<Seller> sellersList = new ArrayList<>();
			for (int k = 0; k < sellers.length; ++k) {
			 try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=?")) {
				readSellers.setString(1, sellers[k]);
				ResultSet rs = readSellers.executeQuery();
				if (rs.next())
				 sellersList.add(new Seller(sellers[k], rs.getLong("id")));
			 } catch (SQLException e) {
				throw new RuntimeException(e);
			 }
			}
			suppliers = new Supplier(id, name, sellersList);
		 }
		 else suppliers=new Supplier(id, name);
		}
		else suppliers = new Supplier(id, name);
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return suppliers;
 }

 public List<Supplier> find(){
	List<Supplier> suppliers = new ArrayList<>();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readSuppliers = connect.prepareStatement("SELECT id, name, sellers FROM suppliers")) {
	 ResultSet result = readSuppliers.executeQuery();
	 while (result.next()) {
		long i = result.getLong("id");
		String name = result.getString("name");
		Array arr = result.getArray("sellers");
		if (arr != null) {
		 String[] sellers = (String[]) arr.getArray();
		 if(sellers.length>0) {
			List<Seller> sellersList = new ArrayList<>();
			for (int k = 0; k < sellers.length; ++k) {
			 try (PreparedStatement readSellers = connect.prepareStatement("SELECT id FROM sellers WHERE name=?")) {
				readSellers.setString(1, sellers[k]);
				ResultSet rs = readSellers.executeQuery();
				if (rs.next())
				 sellersList.add(new Seller(sellers[k], rs.getLong("id")));
			 } catch (SQLException e) {
				throw new RuntimeException(e);
			 }
			}
			suppliers.add(new Supplier(i, name, sellersList));
		 }
		 else suppliers.add(new Supplier(i, name));
		}
		else suppliers.add(new Supplier(i, name));
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return suppliers;
 }
}