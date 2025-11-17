package com.e_commerce.project.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.repositry.ProductRepositry;
import com.e_commerce.project.service.Productservice;

@Service
public class ProductServiceImpl implements Productservice {

    @Autowired
    private ProductRepositry productRepositry;

    @Override
    public List<Product> getAllProducts() {
        return productRepositry.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        System.out.println("Saving product: " + product.getProductname());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepositry.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepositry.deleteById(id);
    }

    @Override
    public boolean updateProduct(Long id, Product product) {
        return productRepositry.findById(id).map(existingProduct -> {
            existingProduct.setProductname(product.getProductname());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStockquantity(product.getStockquantity());
            productRepositry.save(existingProduct);
            return true;
        }).orElse(false);
    }
}
