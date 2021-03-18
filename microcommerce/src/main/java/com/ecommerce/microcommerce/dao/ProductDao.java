package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;

import java.util.List;

public interface ProductDao {
    public List<Product> findALl();

    public void ProductfindById(int id);

    public void Productsave(Product product);
}
