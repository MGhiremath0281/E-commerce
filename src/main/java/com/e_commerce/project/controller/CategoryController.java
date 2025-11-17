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

import com.e_commerce.project.model.Categry;
import com.e_commerce.project.service.Categoryservice;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private Categoryservice categoryservice;

    // Accessible by everyone (users, vendors, admins)
    @GetMapping
    public ResponseEntity<List<Categry>> getCategories() {
        return ResponseEntity.ok(categoryservice.getCategories());
    }

    // Only Admin can create new categories
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Categry category) {
        categoryservice.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category added successfully");
    }

    // Only Admin can delete categories
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryservice.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    // Both Admin and Vendor can update categories
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Categry category) {
        boolean updated = categoryservice.updateCategory(id, category);

        if (updated) {
            return ResponseEntity.ok("Category updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found");
        }
    }
}
