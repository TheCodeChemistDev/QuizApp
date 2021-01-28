package com.thecodechemist.quizapp;

import android.util.Log;

import java.util.HashMap;

public class APIHelper {

    private String urlBase = "https://opentdb.com/api.php?amount=";
    private int questionAmount;
    private String difficulty;
    private String category;
    private HashMap categoryMap;
    private String type;

    public APIHelper(int questionAmount, String difficulty, String category, String type) {
        this.questionAmount = questionAmount;
        this.difficulty = difficulty;
        this.category = category;
        this.type = type;

        this.categoryMap = new HashMap<String, Integer>();
        categoryMap.put("General Knowledge", 9);
        categoryMap.put("Entertainment: Books", 10);
        categoryMap.put("Entertainment: Film", 11);
        categoryMap.put("Entertainment: Music", 12);
        categoryMap.put("Entertainment: Musicals and Theatre", 13);
        categoryMap.put("Entertainment: Television", 14);
        categoryMap.put("Entertainment: Video Games", 15);
        categoryMap.put("Entertainment: Board Games", 16);
        categoryMap.put("Science and Nature", 17);
        categoryMap.put("Science: Computers", 18);
        categoryMap.put("Science: Mathematics", 19);
        categoryMap.put("Mythology", 20);
        categoryMap.put("Sports", 21);
        categoryMap.put("Geography", 22);
        categoryMap.put("History", 23);
        categoryMap.put("Politics", 24);
        categoryMap.put("Art", 25);
        categoryMap.put("Celebrities", 26);
        categoryMap.put("Animals", 27);
        categoryMap.put("Vehicles", 28);
        categoryMap.put("Entertainment: Comics", 29);
        categoryMap.put("Science: Gadgets", 30);
        categoryMap.put("Entertainment: Japanese Anime and Manga", 31);
        categoryMap.put("Entertainment: Cartoon and Animations", 32);

    }

    public String generateUrl() {
        String fullUrl = "";
        fullUrl += urlBase;
        fullUrl += questionAmount;

        if(!difficulty.equals("Any")) {
            fullUrl += "&difficulty=";
            fullUrl += difficulty.toLowerCase();
        }

        if(!category.equals("Any")) {
            fullUrl += "&category=";
            fullUrl += categoryMap.get(category);
        }

        if(!type.equals("Any")) {
            fullUrl += "&type=";
            if(type.equals("Multiple Choice")) {
                fullUrl += "multiple";
            } else {
                fullUrl += "boolean";
            }
        }

        Log.e("Full API URL", fullUrl);
        return fullUrl;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

}
