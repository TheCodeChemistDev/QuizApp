package com.thecodechemist.quizapp;

public class Game {

    private int score;
    private int currentQuestionNo;
    private int questionIndex;
    private int questionCount;

    public Game(int questionCount) {
        this.score = 0;
        this.currentQuestionNo = 0;
        this.questionIndex = -1;
        this.questionCount = questionCount;
    }

    public void nextQuestion() {
        this.currentQuestionNo += 1;
        this.questionIndex += 1;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentQuestionNo() {
        return currentQuestionNo;
    }

    public void setCurrentQuestionNo(int currentQuestionNo) {
        this.currentQuestionNo = currentQuestionNo;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }
}
