package com.thecodechemist.quizapp;

import android.util.Log;
import android.widget.Button;

public class Question {

    private String question;
    private String correctAnswer;
    private String incorrectAnswer1;
    private String incorrectAnswer2;
    private String incorrectAnswer3;
    private String type;

    public Question(String q, String c, String i1, String type) {
        this.question = q;
        this.correctAnswer = c;
        this.incorrectAnswer1 = i1;
        this.type = type;

    }

    public Question(String q, String c, String i1, String i2, String i3, String type) {
        this.question = q;
        this.correctAnswer = c;
        this.incorrectAnswer1 = i1;
        this.incorrectAnswer2 = i2;
        this.incorrectAnswer3 = i3;
        this.type = type;

    }

    public boolean checkAnswer(Button btnPressed) {
        String playerAnswer = btnPressed.getText().toString();
        String correctAnswer = this.correctAnswer;
        Log.e("Player Answer: ", playerAnswer);
        Log.e("Correct Answer: ", correctAnswer);
        if (playerAnswer.equals(correctAnswer)) {
            return true;
        } else {
            return false;
        }
    }

    //Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIncorrectAnswer1() {
        return incorrectAnswer1;
    }

    public void setIncorrectAnswer1(String incorrectAnswer1) {
        this.incorrectAnswer1 = incorrectAnswer1;
    }

    public String getIncorrectAnswer2() {
        return incorrectAnswer2;
    }

    public void setIncorrectAnswer2(String incorrectAnswer2) {
        this.incorrectAnswer2 = incorrectAnswer2;
    }

    public String getIncorrectAnswer3() {
        return incorrectAnswer3;
    }

    public void setIncorrectAnswer3(String incorrectAnswer3) {
        this.incorrectAnswer3 = incorrectAnswer3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
