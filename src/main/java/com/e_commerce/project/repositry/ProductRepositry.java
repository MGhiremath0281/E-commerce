package com.e_commerce.project.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.project.model.Product;

@Repository
public interface ProductRepositry extends JpaRepository<Product, Long> {

}
