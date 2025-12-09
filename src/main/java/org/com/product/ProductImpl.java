package org.com.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements ProductInterface {
	
	
	private Connection connection;

    public ProductImpl(Connection connection) {
        this.connection = connection;
    }

	@Override
	public List<ProductModel> getProductsByCategory(int categoryId) throws ProductException {
		// TODO Auto-generated method stub
		
		String sql = "SELECT * FROM products WHERE categoryId = ?"; 
		
		List<ProductModel> products = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(sql)) {
			
			ps.setInt(1, categoryId);
			
			try(ResultSet rs = ps.executeQuery()){
				
				while(rs.next()) {
					 ProductModel product = new ProductModel(
	                            rs.getInt("productId"),
	                            rs.getString("productName"),
	                            rs.getString("productDescription"),
	                            rs.getFloat("productPrice"),
	                            rs.getString("productImageUrl"), 
	                            categoryId
	                 );
					 
					 products.add(product); 
					 
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ProductException("Error fetching products for category " + categoryId, e);
	        
		}
		return products;
	}
	
	
	@Override
    public ProductModel getProductById(int productId) throws ProductException {

        String sql = "SELECT productId, productName, productDescription, productPrice, productImageUrl, categoryId " +
                     "FROM products WHERE productId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new ProductModel(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("productDescription"),
                            rs.getFloat("productPrice"),
                            rs.getString("productImageUrl"),
                            rs.getInt("categoryId")
                    );
                }
            }

        } catch (SQLException e) {
        	
            throw new ProductException("Error fetching product with ID " + productId, e);
        }

        return null; // Not found
    }
	
	 @Override
	    public boolean addProduct(ProductModel product) throws ProductException {

	        String sql = "INSERT INTO products (productName, productDescription, productPrice, productImageUrl, categoryId) " +
	                     "VALUES (?, ?, ?, ?, ?)";

	        try (PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setString(1, product.getProductName());
	            
	            ps.setString(2, product.getProductDescription());
	            
	            ps.setFloat(3, product.getProductPrice());
	            
	            ps.setString(4, product.getProductImageLink());
	            
	            ps.setInt(5, product.getCategoryId());

	            return ps.executeUpdate() > 0;

	        } catch (SQLException e) {
	        	
	            throw new ProductException("Error adding new product", e);
	        }
	    }

	 
	 @Override
    public boolean updateProduct(ProductModel product) throws ProductException {

        String sql = "UPDATE products SET productName = ?, productDescription = ?, productPrice = ?, " +
                     "productImageUrl = ?, categoryId = ? WHERE productId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, product.getProductName());
            
            ps.setString(2, product.getProductDescription());
            
            ps.setFloat(3, product.getProductPrice());
            
            ps.setString(4, product.getProductImageLink());
            
            ps.setInt(5, product.getCategoryId());
            
            ps.setInt(6, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
        	
            throw new ProductException("Error updating product with ID " + product.getProductId(), e);
        }
    }
	 
	 @Override
    public boolean deleteProduct(int productId) throws ProductException {

        String sql = "DELETE FROM products WHERE productId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
        	
            throw new ProductException("Error deleting product with ID " + productId, e);
        }
    }
}
