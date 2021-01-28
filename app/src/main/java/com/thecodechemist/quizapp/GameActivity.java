package com.thecodechemist.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements CallBackListener {

    ArrayList<Question> questions = new ArrayList<>();
    TextView tvQuestion, tvCorrect, tvScore;
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnNextQuestion, btnEndGame;
    Game game;

    @Override
    public void callback() {
        initGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvQuestion = findViewById(R.id.tvGameQuestion);
        tvCorrect = findViewById(R.id.tvGameCorrect);
        tvScore = findViewById(R.id.tvGameScore);
        btnAnswer1 = findViewById(R.id.btnGameAnswer1);
        btnAnswer2 = findViewById(R.id.btnGameAnswer2);
        btnAnswer3 = findViewById(R.id.btnGameAnswer3);
        btnAnswer4 = findViewById(R.id.btnGameAnswer4);
        btnNextQuestion = findViewById(R.id.btnGameNextQuestion);

        Intent i = getIntent();
        int numberOfQuestions = i.getIntExtra("NumberOfQuestions", 5);
        String difficulty = i.getStringExtra("Difficulty");
        String category = i.getStringExtra("Category");
        String type = i.getStringExtra("QuestionType");
        game = new Game(numberOfQuestions);
        APIHelper apiHelper = new APIHelper(numberOfQuestions, difficulty, category, type);
        QuestionDownloadTask downloadTask = new QuestionDownloadTask(questions);
        downloadTask.setListener(this);
        downloadTask.execute(apiHelper.generateUrl());

        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGuess(v);

            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGuess(v);

            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGuess(v);

            }
        });

        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGuess(v);

            }
        });

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

    }

    public void initGame() {
        //Set visibility of question and answers, and load the first question
        tvQuestion.setVisibility(View.VISIBLE);
        tvScore.setVisibility(View.VISIBLE);
        btnAnswer1.setVisibility(View.VISIBLE);
        btnAnswer2.setVisibility(View.VISIBLE);
        btnAnswer3.setVisibility(View.VISIBLE);
        btnAnswer4.setVisibility(View.VISIBLE);

        nextQuestion();

    }

    public void updateUIForMultiple() {
        //Hide the views not required for answering a question and enable answer buttons
        tvCorrect.setVisibility(View.INVISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);
        btnAnswer3.setVisibility(View.VISIBLE);
        btnAnswer4.setVisibility(View.VISIBLE);
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);

        //Switch to randomise order of answers on buttons
        int rand = new Random().nextInt(3);
        switch(rand) {
            case 0:
                btnAnswer1.setText(questions.get(game.getQuestionIndex()).getCorrectAnswer());
                btnAnswer2.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer1());
                btnAnswer3.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer2());
                btnAnswer4.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer3());
                break;
            case 1:
                btnAnswer4.setText(questions.get(game.getQuestionIndex()).getCorrectAnswer());
                btnAnswer1.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer1());
                btnAnswer2.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer2());
                btnAnswer3.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer3());
                break;
            case 2:
                btnAnswer3.setText(questions.get(game.getQuestionIndex()).getCorrectAnswer());
                btnAnswer4.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer1());
                btnAnswer1.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer2());
                btnAnswer2.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer3());
                break;
            case 3:
                btnAnswer2.setText(questions.get(game.getQuestionIndex()).getCorrectAnswer());
                btnAnswer3.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer1());
                btnAnswer4.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer2());
                btnAnswer1.setText(questions.get(game.getQuestionIndex()).getIncorrectAnswer3());
                break;
        }
    }

    public void updateUIForBoolean() {
        //Hide the views not required for answering a question and enable answer buttons
        tvCorrect.setVisibility(View.INVISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);
        btnAnswer1.setText("True");
        btnAnswer2.setText("False");
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setVisibility(View.INVISIBLE);
        btnAnswer4.setVisibility(View.INVISIBLE);

    }

    //OnClick method for btnNextQuestion
    public void nextQuestion() {

        game.nextQuestion();
        //End the game if that was the final question.
        if (game.getCurrentQuestionNo() > game.getQuestionCount()) {
            endGame();
        } else {
            //Set the views up for the current question
            tvQuestion.setText(questions.get(game.getQuestionIndex()).getQuestion());
            if(questions.get(game.getQuestionIndex()).getType().equals("multiple")) {
                updateUIForMultiple();
            } else {
                updateUIForBoolean();
            }
        }
    }

    //OnClick method for Answer Buttons
    public void makeGuess(View v) {
        Button btnPressed = (Button) v;

        //Check the answer and display correct/incorrect
        boolean correct = questions.get(game.getQuestionIndex()).checkAnswer(btnPressed);
        Log.e("Question Correct?", Boolean.toString(correct));
        if(correct) {
            game.setScore(game.getScore() + 1);
            tvCorrect.setText("Correct!");
        } else {
            tvCorrect.setText("Incorrect!");
        }

        //Update the score on UI
        int currentQ = game.getCurrentQuestionNo();
        int score = game.getScore();
        tvScore.setText(score + " / " + currentQ);

        //Disable answer buttons and show next question button
        tvCorrect.setVisibility(View.VISIBLE);
        btnNextQuestion.setVisibility(View.VISIBLE);
        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);
        btnAnswer4.setEnabled(false);

    }

    //Run when the specified question limit has been reached.
    public void endGame() {
        tvQuestion.setVisibility(View.INVISIBLE);
        tvScore.setVisibility(View.INVISIBLE);
        btnAnswer1.setVisibility(View.INVISIBLE);
        btnAnswer2.setVisibility(View.INVISIBLE);
        btnAnswer3.setVisibility(View.INVISIBLE);
        btnAnswer4.setVisibility(View.INVISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);

        //Put the score calculations and tvCorrect text methods into a method in the Game object
        double score = game.getScore();
        int questionsAnswered = game.getQuestionCount();
        Log.e("Score: ", "=" + score);
        Log.e("Out Of: ", "=" + questionsAnswered);
        Log.e("Percent: ", "=" + score / questionsAnswered);

        double percent = (score / questionsAnswered) * 100;
        if(percent <= 25) {
            tvCorrect.setText("Too bad! You only scored " + percent + "%. Looks like you need to go back to school!");
        } else if (percent <= 50) {
            tvCorrect.setText("You scored " + percent + "%. Could do better!");
        } else if (percent <= 75) {
            tvCorrect.setText("You got over half correct. Well done! you scored " + percent + "%.");
        } else if(percent <= 99.99) {
            tvCorrect.setText("Great job! You nearly got them all right! You score " + percent + "%.");
        } else {
            tvCorrect.setText("Incredible! You got a perfect score! " + percent + "%!");
        }

        btnEndGame = findViewById(R.id.btnGameEndGame);
        btnEndGame.setVisibility(View.VISIBLE);
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
}