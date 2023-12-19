package com.quiz.model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private int id;
    private int quizId;
    private String question;
    private ArrayList<String> answers;
    private int correctAnswer;

    public Question(int id, int quizId, String question, ArrayList<String> answers, int correctAnswer) {
        this.id = id;
        this.quizId = quizId;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public Question() {
        this(-1, -1, "", new ArrayList<>(), -1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
