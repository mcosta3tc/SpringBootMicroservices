package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
    Indique à Spring que c'est une classe qui génère des données
 */
@Repository
public class ProductDaoI implements ProductDao {
    public static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, new String("Pc"), 300));
        products.add(new Product(2, new String("Aspi"), 500));
        products.add(new Product(3, new String("Table de Ping Pong"), 1500));
    }

    /*
    Renvoie tous les produits crées
     */
    @Override
    public List<Product> findALl() {
        return products;
    }

    /*
    Vérifie s'il y a un produit avec l'id dans la List<> et le revoie si oui
     */
    @Override
    public Product findById(int id) {
        for (Product product :
                products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /*
    Ajoute le produit reçu à la List<>
     */
    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
