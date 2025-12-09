package org.com.DaoCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryImpl implements CategoryInterface {
	
	 private final Connection connection;

	    public CategoryImpl(Connection connection) {
	        this.connection = connection;
	    }

	    @Override
	    public List<CategoryModel> getAllCategories() throws CategoryException {
	        String query = "SELECT * FROM category";
	        List<CategoryModel> categories = new ArrayList<>();
	        try (PreparedStatement ps = connection.prepareStatement(query);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	CategoryModel c = new CategoryModel(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryDescription"), 
	            			rs.getString("CategoryImageLink"));
	                categories.add(c);
	            }
	        } catch (SQLException e) {
	            throw new CategoryException("Error fetching categories", e);
	        }
	        return categories;
	    }

	    @Override
	    public CategoryModel getCategoryById(int id) throws CategoryException {
	        String query = "SELECT * FROM category WHERE categoryId=?";
	        try (PreparedStatement ps = connection.prepareStatement(query)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return new CategoryModel(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryDescription"), 
	                    	rs.getString("CategoryImageLink"));
	                }
	            }
	        } catch (SQLException e) {
	            throw new CategoryException("Error fetching category by ID", e);
	        }
	        return null;
	    }

	    @Override
	    public void addCategory(CategoryModel category) throws CategoryException {
	        String query = "INSERT INTO category (categoryName) VALUES (?)";
	        try (PreparedStatement ps = connection.prepareStatement(query)) {
	            ps.setString(1, category.getCategoryName());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            throw new CategoryException("Error adding category", e);
	        }
	    }

	    @Override
	    public void updateCategory(CategoryModel category) throws CategoryException {
	        String query = "UPDATE category SET categoryName=? WHERE categoryId=?";
	        try (PreparedStatement ps = connection.prepareStatement(query)) {
	            ps.setString(1, category.getCategoryName());
	            ps.setInt(2, category.getCategoryId());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            throw new CategoryException("Error updating category", e);
	        }
	    }

	    @Override
	    public void deleteCategory(int id) throws CategoryException {
	        String query = "DELETE FROM category WHERE categoryId=?";
	        try (PreparedStatement ps = connection.prepareStatement(query)) {
	            ps.setInt(1, id);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            throw new CategoryException("Error deleting category", e);
	        }
	    }

}
