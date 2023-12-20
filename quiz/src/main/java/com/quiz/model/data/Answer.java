package com.quiz.model.data;

import java.io.Serializable;

public class Answer implements Serializable {
    int id;
    int idUser;
    int idQuiz;
    double point;
    int numCorrect;
    String dateCreated;
    String nameUser;
    String nameQuiz;

    public Answer() {
        id = 0;
        idUser = 0;
        point = 0;
        numCorrect = 0;
        dateCreated = "";
        nameUser = "";
        nameQuiz = "";
    }

    public Answer(int idUser, int idQuiz) {
        this.idUser = idUser;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }
}
