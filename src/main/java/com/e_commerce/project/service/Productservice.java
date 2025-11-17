package com.e_commerce.project.service;

import java.util.List;

import com.e_commerce.project.model.Product;

public interface Productservice {
    List<Product> getAllProducts();

    Product createProduct(Product product);

    void deleteProduct(Long id);

    boolean updateProduct(Long id, Product product);
}
