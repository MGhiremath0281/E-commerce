package com.e_commerce.project.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.project.model.Categry;
import com.e_commerce.project.repositry.CategoryRepositry;
import com.e_commerce.project.service.Categoryservice;

@Service
public class CategoryserviceImpl implements Categoryservice {

    @Autowired
    private CategoryRepositry categoryRepositry;

    @Override
    public List<Categry> getCategories() {
        return categoryRepositry.findAll();
    }

    @Override
    public Categry createCategory(Categry categry) {
        return categoryRepositry.save(categry);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepositry.deleteById(id);
    }

    @Override
    public boolean updateCategory(Long id, Categry category) {
        return categoryRepositry.findById(id).map(existingCategory -> {
            existingCategory.setCategoryname(category.getCategoryname());
            categoryRepositry.save(existingCategory);
            return true;
        }).orElse(false);
    }
}
