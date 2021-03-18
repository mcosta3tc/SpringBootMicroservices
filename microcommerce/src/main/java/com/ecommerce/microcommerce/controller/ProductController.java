package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.web.bind.annotation.*;

//La classe va pouvoir traiter des requêtes et chaque methode va renvoyer un JSON à l'utilisateur
//Spring sait alors que les réponses aux requêtes qu'il passe devront être probablement en JSON
@RestController
public class ProductController {
    /*
         Annotation qui fait le lien avec l'URI /Produits invoqué via GET et la methode listeProduits
         version courte de
         @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    */
    @GetMapping("/Produits")
    public String listeProduits() {
        return "Un exemple de produit";
    }

    /*
         version courte de
         @RequestMapping(value = "/Produits{id}", method = RequestMethod.GET)
    */
    @GetMapping("/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {
        Product product = new Product(id, new String("Aspi"), 100);
        return product;
    }
}
