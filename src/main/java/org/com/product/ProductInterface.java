package org.com.product;

import java.util.List;

public interface ProductInterface {
	
	List<ProductModel> getProductsByCategory(int categoryId) throws ProductException;
	
	ProductModel getProductById(int productId) throws ProductException;

    boolean addProduct(ProductModel product) throws ProductException;

    boolean updateProduct(ProductModel product) throws ProductException;

    boolean deleteProduct(int productId) throws ProductException;
}
