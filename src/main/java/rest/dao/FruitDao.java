package rest.dao;

import rest.model.Fruit;
import rest.model.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDao {
 public Fruit find(long id) {
	Fruit fruits = new Fruit();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT name, price, sellers FROM fruit WHERE id=?")) {
	 readFruit.setLong(1, id);
	 ResultSet result = readFruit.executeQuery();
	 if (result.next()) {
		String name = result.getString("name");
		int price = result.getInt("price");
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
			fruits = new Fruit(id, name, price, sellersList);
		 }
		 else fruits = new Fruit(id, name, price);
		}
		else fruits = new Fruit(id, name, price);
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return fruits;
 }

 public List<Fruit> find() {
	List<Fruit> fruits = new ArrayList<>();
	Connection connect = DatabaseConnector.connector();
	try ( PreparedStatement readFruit = connect.prepareStatement("SELECT id, name, price, sellers FROM fruit")) {
	 ResultSet result = readFruit.executeQuery();
	 while (result.next()) {
		long i = result.getLong("id");
		String name = result.getString("name");
		int price = result.getInt("price");
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
			fruits.add(new Fruit(i, name, price, sellersList));
		 }
		 else fruits.add(new Fruit(i, name, price));
		}
		else fruits.add(new Fruit(i, name, price));
	 }
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return fruits;
 }

 public long save(Fruit fruit) {
	String name = fruit.getName();
	double pr = fruit.getPrice();
	long id = 0;
	if(pr<=0)
	 return id;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement addFruit = connect.prepareStatement("INSERT INTO fruit(name, price) VALUES(?,?)")) {
	 addFruit.setString(1, name);
	 addFruit.setDouble(2, pr);
	 addFruit.executeUpdate();
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and price=?")){
	 readFruit.setString(1, name);
	 readFruit.setDouble(2, pr);
	 ResultSet result = readFruit.executeQuery();
	 if (result.next())
		id = result.getLong("id");
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public long updateSellers(Fruit fruit) {
	String name = fruit.getName();
	long id=0;
	String seller = fruit.getSeller();
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and ?=ANY(sellers)")) {
	 readFruit.setString(1, name);
	 readFruit.setString(2, seller);
	 ResultSet rs = readFruit.executeQuery();
	 if (rs.next()) {
		id = rs.getLong("id");
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateFruit = connect.prepareStatement("UPDATE fruit SET sellers=array_append(sellers,?) WHERE name=?")){
	 updateFruit.setString(1, seller);
	 updateFruit.setString(2, name);
	 updateFruit.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and ?=ANY(sellers)")) {
	 readFruit.setString(1, name);
	 readFruit.setString(2, seller);
	 ResultSet rs = readFruit.executeQuery();
	 if(rs.next())
		id = rs.getLong("id");
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM sellers WHERE name=? and ?=ANY(fruits)")) {
	 readFruit.setString(1, seller);
	 readFruit.setString(2, name);
	 ResultSet rs = readFruit.executeQuery();
	 if (rs.next()) {
		connect.close();
		return id;
	 }
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try(PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET fruits=array_append(fruits,?) WHERE name=?")){
	 updateSellers.setString(1, name);
	 updateSellers.setString(2, seller);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public long updatePrice(Fruit fruit) {
	long id=0;
	String name = fruit.getName();
	double price = fruit.getPrice();
	Connection connect = DatabaseConnector.connector();
	try(PreparedStatement updateFruit = connect.prepareStatement("UPDATE fruit SET price=? WHERE name=?")){
	 updateFruit.setDouble(1, price);
	 updateFruit.setString(2, name);
	 updateFruit.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=? and price=?")) {
	 readFruit.setString(1, name);
	 readFruit.setDouble(2, price);
	 ResultSet rs = readFruit.executeQuery();
	 if(rs.next())
		id = rs.getLong("id");
	 connect.close();
	}catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }

 public long delete(Fruit fruit) {
	String name = fruit.getName();
	long id=0;
	Connection connect = DatabaseConnector.connector();
	try (PreparedStatement readFruit = connect.prepareStatement("SELECT id FROM fruit WHERE name=?")) {
	 readFruit.setString(1, name);
	 ResultSet rs= readFruit.executeQuery();
	 if(rs.next())
		id=rs.getLong("id");
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement deleteFruit = connect.prepareStatement("DELETE FROM fruit WHERE name=?")) {
	 deleteFruit.setString(1, name);
	 deleteFruit.executeUpdate();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	try (PreparedStatement updateSellers = connect.prepareStatement("UPDATE sellers SET fruits=array_remove(fruits,?)")) {
	 updateSellers.setString(1, name);
	 updateSellers.executeUpdate();
	 connect.close();
	} catch (SQLException e) {
	 throw new RuntimeException(e);
	}
	return id;
 }
}
