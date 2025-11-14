package com.e_commerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.project.model.Categry;
import com.e_commerce.project.service.Categoryservice;

@RestController
public class CategoryController {

    @Autowired
    private Categoryservice categoryservice;

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Categry>> getCategories() {
        return ResponseEntity.ok(categoryservice.getCategories());
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Categry category) {
        categoryservice.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category added successfully");
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        String result = categoryservice.deleteCategory(id);

        if (result.contains("deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(result);
        }
    }

    @PutMapping("/api/admin/categories/{id}")
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
