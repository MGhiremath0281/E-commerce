package com.e_commerce.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.project.model.Categry;

@RestController
public class CategoryController {

    private List<Categry> categry = new ArrayList<>();

    @GetMapping("api/public/categories")
    public List<Categry> getACategries() {
        return categry;
    }

}
