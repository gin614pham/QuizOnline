package com.quiz.model;

import java.rmi.Remote;

import com.quiz.model.data.Answer;
import com.quiz.model.data.AnswerQuiz;
import com.quiz.model.data.Question;
import com.quiz.model.data.Quiz;
import com.quiz.model.data.User;

import java.util.ArrayList;

public interface ServerImp extends Remote {

    public void registerClient(ClientImp client) throws Exception;

    public void unregisterClient(ClientImp client) throws Exception;

    public User login(String email, String password) throws Exception;

    public User register(String email, String password, String name) throws Exception;

    public ArrayList<Quiz> getLast10Quizzes() throws Exception;

    public ArrayList<Quiz> search(String label) throws Exception;

    public boolean addQuiz(Quiz quiz, ArrayList<Question> questions) throws Exception;

    public ArrayList<Quiz> getQuizzesByUserId(int userId) throws Exception;

    public ArrayList<Quiz> search(int userId, String label) throws Exception;

    public ArrayList<Question> getQuestionsByQuizId(int quizId) throws Exception;

    public Answer doQuiz(int userId, int quizId, ArrayList<AnswerQuiz> answers) throws Exception;

    public ArrayList<Answer> getHistoryByUserId(int userId) throws Exception;

    public ArrayList<Answer> getHistoryByQuizId(int quizId) throws Exception;

    public ArrayList<Quiz> getTopQuizzesWithMostAnswers() throws Exception;

    public boolean deleteQuiz(int quizId) throws Exception;

    public boolean updateQuiz(Quiz quiz, ArrayList<Question> questions) throws Exception;

}
