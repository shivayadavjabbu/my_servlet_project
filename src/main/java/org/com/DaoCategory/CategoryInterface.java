package org.com.DaoCategory;

import java.util.List;

public interface CategoryInterface {

    // Get all categories
    List<CategoryModel> getAllCategories() throws CategoryException;

    // Get a category by ID
    CategoryModel getCategoryById(int id) throws CategoryException;

    // Add a new category
    void addCategory(CategoryModel category) throws CategoryException;

    // Update an existing category
    void updateCategory(CategoryModel category) throws CategoryException;

    // Delete a category by ID
    void deleteCategory(int id) throws CategoryException;
}
