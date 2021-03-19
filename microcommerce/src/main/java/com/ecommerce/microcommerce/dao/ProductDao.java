package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    Quand héritez de JpaRepository on indique l'entité et le type de son ID
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    /*
        utile pour les données en brut, sans base de donnée
        public List<Product> findALl();

        public Product findById(int id);

        public Product save(Product product);
    */

    //Génère le SQL automatiquement en partant du nom de la méthode, grâce aux conventions de Spring Data JPA
    Product findById(int id);
    /*
        findBy = SELECT
        Prix = le nom de la propriété sur laquelle le SELECT s'applique
        GreaterThan = condition

        SELECT * FROM product WHERE prix > [un chiffre]
     */
    List<Product> findByPrixGreaterThan(int prixLimit);

    /*
        Requête qui retourne les produits dont le nom ressemble à la chaine
     */
    List<Product> findByNomLike(String recherche);
}
