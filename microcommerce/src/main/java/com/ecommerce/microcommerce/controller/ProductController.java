package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.dao.ProductDaoI;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//La classe va pouvoir traiter des requêtes et chaque methode va renvoyer un JSON à l'utilisateur
//Spring sait alors que les réponses aux requêtes qu'il passe devront être probablement en JSON
@RestController
public class ProductController {

    /*
    Spring Crée une instance de l'interface, productDao a désormais accès à toutes les méthodes defines
     */
    @Autowired
    private ProductDao productDao;

    /*
         Annotation qui fait le lien avec l'URI /Produits invoqué via GET et la methode listeProduits
         version courte de
         @RequestMapping(value = "/Produits", method = RequestMethod.GET)

    @GetMapping("/Produits")
    public String listeProduits() {
        return "Un exemple de produit";
    }
    */
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public List<Product> listProduits() {
        return productDao.findALl();
    }


    /*
         version courte de
         @RequestMapping(value = "/Produits{id}", method = RequestMethod.GET)

    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product product = new Product(id, new String("Aspi"), 100);
        return product;
    }
    */
    @RequestMapping(value = "Produits/{id}", method = RequestMethod.GET)
    public Product afficherUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    /*
    Ajoute un Produit
    @RequestBody: Spring Boot a configuré "Jackson" pour convertir les objets en Java renvoyé en réponse en JSON (GET). Ici c'est donc le contraire, le Body de est converti en objet JAVA. (La librairie qu'utilise Spring est "Jackson" c'est aussi possible avec "Gson")
    La requête est donc convertie ici en objet Product
     */
    @PostMapping(value = "/Produits")
    public void ajouterProduit(@RequestBody Product product) {
        productDao.save(product);
    }
}
