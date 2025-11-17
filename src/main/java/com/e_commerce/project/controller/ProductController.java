package com.e_commerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.service.Productservice;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private Productservice productservice;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productservice.getAllProducts());
    }

    @PreAuthorize("hasAnyRole('VENDOR')")
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productservice.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product created successfully");
    }

    @PreAuthorize("hasAnyRole('VENDOR','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productservice.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PreAuthorize("hasAnyRole('VENDOR','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        boolean updated = productservice.updateProduct(id, product);

        if (updated) {
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " not found");
        }
    }
}
