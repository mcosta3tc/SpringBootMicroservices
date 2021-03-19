package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
  Informations que nous ne souhaitons pas exposer
  @JsonIgnoreProperties: method Jackson pour faire ce travail
  @JsonIgnoreProperties(value = {"prixAchat", "id"}) (en dur non pas en fonction de qui le demande)
 */
//Indique que ce Bean accept un filtre
//@JsonFilter("monFiltreDynamique")

/*
    La class est scannée et prise en compte il n'y pas besoin de passer par le traditionnel fichier persistence.xml
 */
@Entity
public class Product {

    /*
        Attribut annoté comme clef unique auto-générer
     */
    @Id
    @GeneratedValue
    private int id;

    private String nom;
    private int prix;

    /*
      Information que nous ne souhaitons pas exposer
      @JsonIgnore: method Jackson pour faire ce travail en dur
     */
    private int prixAchat;

    //constructeur par défaut
    public Product() {
    }

    //constructeur pour les tests
    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
