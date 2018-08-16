package com.afeka.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String username;
    int age;
    EditText username_input;
    EditText age_input;
    Button button_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username_input = findViewById(R.id.txt_name);
        age_input = findViewById(R.id.txt_age);
        button_go = findViewById(R.id.btn_go);

        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               button_go_clicked();
            }
        });
    }


    private void button_go_clicked(){

        if (username_input.getText().length() == 0 || age_input.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "please fill all fields!!", Toast.LENGTH_SHORT).show();
            return;
        }

        username = username_input.getText().toString();
        age = Integer.valueOf(age_input.getText().toString());

        Intent LevelActivity_intent = new Intent(this, LevelActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_USERNAME",username);
        //extras.putString("EXTRA_AGE",String.valueOf(age));
        LevelActivity_intent.putExtras(extras);

        startActivity(LevelActivity_intent);
    }
}
