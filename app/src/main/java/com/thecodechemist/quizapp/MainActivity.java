package com.thecodechemist.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spnrNumberOfQuestions = findViewById(R.id.spnrMainMenuNumberOfQuestions);
        ArrayAdapter<CharSequence> numberOfQuestionsAdapter = ArrayAdapter.createFromResource(this,
                R.array.numberOfQuestionChoices, android.R.layout.simple_spinner_dropdown_item);
        spnrNumberOfQuestions.setAdapter(numberOfQuestionsAdapter);

        Spinner spnrDifficulty = findViewById(R.id.spnrMainMenuDifficulty);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficultyChoices, android.R.layout.simple_spinner_dropdown_item);
        spnrDifficulty.setAdapter(difficultyAdapter);

        Spinner spnrCategory = findViewById(R.id.spnrMainMenuCategory);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryChoices, android.R.layout.simple_spinner_dropdown_item);
        spnrCategory.setAdapter(categoryAdapter);

        Spinner spnrQuestionType = findViewById(R.id.spnrMainMenuQuestionType);
        ArrayAdapter<CharSequence> questionTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.questionTypeChoices, android.R.layout.simple_spinner_dropdown_item);
        spnrQuestionType.setAdapter(questionTypeAdapter);

        Button btnPlay = findViewById(R.id.btnMainMenuPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                int numberOfQuestions = Integer.parseInt(spnrNumberOfQuestions.getSelectedItem().toString());
                String difficulty = spnrDifficulty.getSelectedItem().toString();
                String category = spnrCategory.getSelectedItem().toString();
                String questionType = spnrQuestionType.getSelectedItem().toString();
                i.putExtra("NumberOfQuestions", numberOfQuestions);
                i.putExtra("Difficulty", difficulty);
                i.putExtra("Category", category);
                i.putExtra("QuestionType", questionType);
                startActivity(i);
            }
        });
    }
}