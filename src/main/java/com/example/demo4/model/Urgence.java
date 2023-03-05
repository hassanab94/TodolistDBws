package com.example.demo4.model;

public class Urgence {
    private static int idUrgence;
    private String nom;

    public Urgence(int idUrgence, String nom) {
        this.idUrgence = idUrgence;
        this.nom = nom;
    }

    public static int getIdUrgence() {
        return idUrgence;
    }

    public void setIdUrgence(int idUrgence) {
        this.idUrgence = idUrgence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
