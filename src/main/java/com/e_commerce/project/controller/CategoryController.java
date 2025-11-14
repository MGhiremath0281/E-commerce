package com.e_commerce.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.project.model.Categry;

@RestController
public class CategoryController {

    private List<Categry> categories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<Categry> getCategories() {
        return categories;
    }

    @PostMapping("/api/public/categories")
    public String createCategory(@RequestBody Categry category) {
        categories.add(category);
        return "Category added successfully";
    }
}
