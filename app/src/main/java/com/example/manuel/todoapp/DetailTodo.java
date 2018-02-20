package com.example.manuel.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailTodo extends AppCompatActivity {
    OrmDbHelper ormDbHelper;
    MainActivity mainActivity;
    TextView todoTitle;
    TextView todoDes;
    TextView todoPrio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        ormDbHelper = OrmDbHelper.getInstance(this);

    }

    public void tost(View view){
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void saveTodo(View view) {
            todoTitle = (TextView) findViewById(R.id.titleText);
             String StringTodoTitle = String.valueOf(todoTitle.getText());

            todoDes = (TextView) findViewById(R.id.descriptionText);
            String StringTodoDes = String.valueOf(todoDes.getText());

           // todoDes = (TextView) findViewById(R.id.titleText);
            String StringTodoPrio = String.valueOf(todoTitle.getText());

            ormDbHelper.insertNewTask(StringTodoTitle, StringTodoDes, StringTodoPrio);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

    }
}