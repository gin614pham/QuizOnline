package com.quiz.model.data;

import java.util.ArrayList;
import java.io.Serializable;

public class Quiz implements Serializable {
    int id;
    String name;
    int numQuestions;
    String author;
    ArrayList<Question> questions;

    public Quiz(int id, String name, int numQuestions, String author) {
        this.id = id;
        this.name = name;
        this.numQuestions = numQuestions;
        this.author = author;
    }

    public Quiz() {
        id = -1;
        name = "";
        numQuestions = 0;
        author = "";
        questions = new ArrayList<>();
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

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        numQuestions++;
    }

}
