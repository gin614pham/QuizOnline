package com.quiz.model.data;

import java.io.Serializable;

public class AnswerQuiz implements Serializable {
    int id;
    int idUser;
    int idQuiz;
    int idAnswer;
    int idQuestion;
    int selectedAnswer;

    public AnswerQuiz() {
        id = 0;
        idUser = 0;
        idQuiz = 0;
        idAnswer = 0;
        idQuestion = 0;
        selectedAnswer = -1;
    }

    public AnswerQuiz(int idUser, int idQuiz, int idAnswer, int idQuestion, int selectedAnswer) {
        this.idUser = idUser;
        this.idQuiz = idQuiz;
        this.idAnswer = idAnswer;
        this.idQuestion = idQuestion;
        this.selectedAnswer = selectedAnswer;
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

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
