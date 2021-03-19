package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public MappingJacksonValue listeProduits() {
        //Stock le code retourner dans une List<>
        //utilisé pour l'exemple sans base de donnée
        //List<Product> produits = productDao.findALl();

        /*
            Récupère toute les données de l'entité concerné
            les autres opérations auto-générées sont
            delete(int id): supprime le produit correspondant
            count(): le nombre de produits
            save(Product produit): ajoute le produit, elle peut également recevoir un Iterable<> de produits pour en ajouter plusieurs
         */
        Iterable<Product> produits = productDao.findAll();

        /*
            SimpleBeanPropertyFilter permet d'établir des règles de filtrage sur un Bean donné
            La règle serializeAllExcept() exclut uniquement les propriétés à ignorer inverse de filterOutAllExcept() pour tout ignorer sauf..
         */
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        /*
            Indique à Jackson à quel Bean appliquer le filtre
            SimpleFilterProvider() permet de déclarer que les règles de filtrages s'applique à tout les Bean annotés avec "monFiltreDynamique"
         */
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        /*
            Fait le filtre concrètement sur la List<>
         */
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
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

    @PostMapping(value = "/Produits")
    public void ajouterProduit(@RequestBody Product product) {
        productDao.save(product);
    }
    */

    /*
    ResponseEntity<Void> est hérité de HttpEntity, qui permet de définir le code HTTP à retourner.
    ResponseEntity<Void> nous donne la main pour personnaliser le code facilement

    Le methode save() fait appel au DAO

    Si le produit ajouté est vide ou n'existe pas, renvoie 204 No Content (noContent() et build() construit le header avec le code choisi)

    Si tout est ok en plus du code 201 nous avons besoin d'ajouter l'URI vers cette nouvelle ressource créée afin d'être conforme avec HTTP
    Cette instance est ensuite passer comme argument à ResponseEntity. Elle est instancié à partir de l'URL reçu (ne fait de ne pas être codé en dur offre la liberté de modifier l'emplacement du Microservice à volonté)

    Ensuite l'id du produit est ajouté à l'URI avec buildAndExpand()

    Enfin la méthode created() crée renvoie le code 201 avec l'URI
     */
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {

        Product productAdded = productDao.save(product);

        if (productAdded == null) {
            return ResponseEntity.noContent().build();
        } else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productAdded.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    /*
        Affiche les produits dont les prix sont supérieurs à ...
     */
    @GetMapping(value = "test/Produits/{prixLimit}")
    public List<Product> testDeRequetesLimite(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    @GetMapping(value = "test/Produits/recherche/{recherche}")
    public List<Product> testDeRequetesRecherche(@PathVariable String recherche) {
        return productDao.findByNomLike(recherche);
    }

    /*
        deleteById(id) est déjà intégrer à Spring Data
     */
    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){
        productDao.save(product);
    }
}
