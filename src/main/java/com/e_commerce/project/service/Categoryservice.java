package com.e_commerce.project.service;

import java.util.List;

import com.e_commerce.project.model.Categry;

public interface Categoryservice {
    List<Categry> getCategories();

    void createCategory(Categry categry);

    String deleteCategory(Long id);

    boolean updateCategory(Long id, Categry category);
}
