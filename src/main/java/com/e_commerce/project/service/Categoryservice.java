package com.e_commerce.project.service;

import java.util.List;

import com.e_commerce.project.model.Categry;

public interface Categoryservice {
    List<Categry> getCategories();

    Categry createCategory(Categry categry);

    void deleteCategory(Long id);

    boolean updateCategory(Long id, Categry category);

}
