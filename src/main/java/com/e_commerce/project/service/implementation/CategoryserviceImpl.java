package com.e_commerce.project.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.e_commerce.project.model.Categry;
import com.e_commerce.project.service.Categoryservice;

@Service
public class CategoryserviceImpl implements Categoryservice {

    private List<Categry> categories = new ArrayList<>();

    @Override
    public List<Categry> getCategories() {
        return categories;
    }

    @Override
    public void createCategory(Categry categry) {
        categories.add(categry);
    }

    @Override
    public String deleteCategory(Long id) {
        Optional<Categry> categry = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (categry.isPresent()) {
            categories.remove(categry.get());
            return "Category with ID " + id + " deleted successfully";
        } else {
            return "Category with ID " + id + " not found";
        }
    }

    @Override
    public boolean updateCategory(Long id, Categry updatedCategory) {
        Optional<Categry> found = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (found.isPresent()) {
            Categry existing = found.get();
            existing.setCategoryname(updatedCategory.getCategoryname());
            return true;
        } else {
            return false;
        }
    }

}
