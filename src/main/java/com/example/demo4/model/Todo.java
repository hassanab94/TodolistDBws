package com.example.demo4.model;

import java.util.Date;

public class Todo {
    private int idTodo;
    private String nom;
    private String description;
    private Date date;
    private int idUrgence;
    private int idUser;

    public Todo(int idTodo, String nom, String description, Date date, int idUrgence, int idUser) {
        this.idTodo = idTodo;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.idUrgence = idUrgence;
        this.idUser = idUser;
    }

    public Todo() {}

    public int getIdTodo() {
        return idTodo;
    }

    public int getIdUrgence() {
        return idUrgence;
    }

    public void setIdUrgence(int idUrgence) {
        this.idUrgence = idUrgence;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdTodo(int idTodo) {
        this.idTodo = idTodo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
