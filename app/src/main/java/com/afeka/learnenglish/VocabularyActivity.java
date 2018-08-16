package com.afeka.learnenglish;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class VocabularyActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    ArrayList<String> words_list = new ArrayList<>();
    ArrayList<String> meanings_list = new ArrayList<>();
    Random rand = new Random();
    int current_question_index = 0;
    String word;
    String option_1;
    String option_2;
    String option_3;
    String option_4;
    Button option1_button;
    Button option2_button;
    Button option3_button;
    Button option4_button;

    TextView word_textView;
    TextView points_textView;
    String points_string;

    int correct_answer_mum;
    int points;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("words").child("Beginners");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot wordSnapshot : dataSnapshot.getChildren()){
                    String word = wordSnapshot.getKey();
                    String meaning = wordSnapshot.getValue(String.class);
                    words_list.add(word);
                    meanings_list.add(meaning);
                }
                new_question();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        option1_button = findViewById(R.id.button_option1);
        option2_button = findViewById(R.id.button_option2);
        option3_button = findViewById(R.id.button_option3);
        option4_button = findViewById(R.id.button_option4);

        word_textView = findViewById(R.id.word_textView);
        points_textView = findViewById(R.id.points_voc_textView);

        points = 0;
        points_string = points + "";
        points_textView.setText(points_string);


        option1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answer(1);
            }
        });


        option2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answer(2);
            }
        });


        option3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answer(3);
            }
        });


        option4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answer(4);
            }
        });
    }


    private void new_question(){
        if(words_list.size() == 0)
            return;

        word = words_list.get(current_question_index);

        correct_answer_mum = rand.nextInt(4) + 1;

        fill_answers_in_options();

        option1_button.setText(option_1);
        option2_button.setText(option_2);
        option3_button.setText(option_3);
        option4_button.setText(option_4);
        word_textView.setText(word);
    }

    private void fill_answers_in_options(){
        int index1, index2, index3;
        int num_of_words = meanings_list.size();
        if(num_of_words < 5)
            return;

        index1 = rand.nextInt(num_of_words);
        while (index1 == current_question_index)
            index1 = rand.nextInt(num_of_words);

        index2 = rand.nextInt(num_of_words);
        while (index1 == index2 || index2 == current_question_index)
            index2 = rand.nextInt(num_of_words);

        index3 = rand.nextInt(num_of_words);
        while (index1 == index3 || index2 == index3 || index3 == current_question_index)
            index3 = rand.nextInt(num_of_words);

        switch (correct_answer_mum){
            case 1:
                option_1 = meanings_list.get(current_question_index);
                option_2 = meanings_list.get(index1);
                option_3 = meanings_list.get(index2);
                option_4 = meanings_list.get(index3);
                break;
            case 2:
                option_1 = meanings_list.get(index1);
                option_2 = meanings_list.get(current_question_index);
                option_3 = meanings_list.get(index2);
                option_4 = meanings_list.get(index3);
                break;
            case 3:
                option_1 = meanings_list.get(index1);
                option_2 = meanings_list.get(index2);
                option_3 = meanings_list.get(current_question_index);
                option_4 = meanings_list.get(index3);
                break;
            case 4:
                option_1 = meanings_list.get(index1);
                option_2 = meanings_list.get(index2);
                option_3 = meanings_list.get(index3);
                option_4 = meanings_list.get(current_question_index);
                break;
        }
    }

    private void check_answer(int num_answer_selected){
        if(num_answer_selected == correct_answer_mum) {
            points++;
            points_string = points + "";
            points_textView.setText(points_string);
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Incorrect answer", Toast.LENGTH_SHORT).show();

        current_question_index++;
        if(current_question_index >= words_list.size())
            current_question_index = 0;

        new_question();


    }
}
