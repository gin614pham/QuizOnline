package com.quiz.model.data;

import java.io.Serializable;

public class Quiz implements Serializable {
    int id;
    String name;
    int numQuestions;
    String author;
    int idAuthor;
    String dateCreated;

    public Quiz(int id, String name, int numQuestions, String author, int idAuthor, String dateCreated) {
        this.id = id;
        this.name = name;
        this.numQuestions = numQuestions;
        this.author = author;
        this.idAuthor = idAuthor;
        this.dateCreated = dateCreated;
    }

    public Quiz() {
        id = -1;
        name = "";
        numQuestions = 0;
        author = "";
        idAuthor = -1;
        dateCreated = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

}
