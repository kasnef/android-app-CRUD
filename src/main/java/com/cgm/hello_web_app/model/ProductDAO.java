package com.cgm.hello_web_app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	
	public List<Product> getAllProducts(){
		List<Product> list = null;
		Connection conn = null;
		String url, user, password;
		String query;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			///load driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//1. Connect to MySQL Database
			url = "jdbc:mysql://localhost:3306/product-k15pm01";
			user = "root";
			password = "Calangthang@ak1406";
			conn = DriverManager.getConnection(url, user, password);
			//System.out.println(conn);
			//2. execute query statement: SQL
			
			  query = "select * from product"; 
			  pst = conn.prepareStatement(query); 
			  rs = pst.executeQuery();
			  list = new ArrayList<Product>();
			  while(rs.next()) {
				  Integer id = rs.getInt("id");
				  String name = rs.getString("name");
				  String image = rs.getString("image");
				  double price = rs.getDouble("price");
				  
				  Product product = new Product(id, name, price, image);
				  list.add(product);
			  }
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				rs.close();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//3. get result
		
		return list;
	}
	
	public boolean addProduct(Product product) {
		Connection conn = null;
        String url, user, password;
        String query;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	url = "jdbc:mysql://localhost:3306/product-k15pm01";
			user = "root";
			password = "Calangthang@ak1406";
			conn = DriverManager.getConnection(url, user, password);
			
			query = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, product.getName());
			pst.setDouble(2, product.getPrice());
			pst.setString(3, product.getImage());
			
			int rowAffected = pst.executeUpdate();
			if (rowAffected > 0) {
				success = true;
			}
			
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
	}
	
	public boolean deleteProduct(Integer productId) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/product-k15pm01";
            String user = "root";
            String password = "Calangthang@ak1406";
            conn = DriverManager.getConnection(url, user, password);

            String query = "DELETE FROM product WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, productId);
            
            int rowsAffected = pst.executeUpdate();
            success = rowsAffected > 0;
        
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
	
	public boolean updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean success = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/product-k15pm01";
            String user = "root";
            String password = "Calangthang@ak1406";
            conn = DriverManager.getConnection(url, user, password);

            String query = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, product.getName()); // Specify the existing product name
            pst.setDouble(2, product.getPrice());
            pst.setString(3, product.getImage());// Set the new name here
            pst.setInt(4, product.getId());

            int rowsAffected = pst.executeUpdate();
            success = rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }
	
	public static void main(String[] args) {
		ProductDAO productDAO = new ProductDAO();
		productDAO.getAllProducts();
	}

}
