package com.example.manuel.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Manuel on 22.02.2018.
 */

public class AddTodoActivity extends AppCompatActivity implements OnClickListener {

    private static final String LOG = AddTodoActivity.class.getName();

    private OrmDbHelper dbHelper = null;

    private EditText todo_title_et;
    private EditText todo_description_et;
    private EditText todo_date_et;
    private Spinner todo_category_sp;
    private Spinner todo_priority_sp;
    private Button cancel_btn;
    private Button ok_btn;

    private List<Category> categories;
    private List<Priority> priorities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todo_title_et = (EditText) findViewById(R.id.add_todo_title_et);
        todo_description_et = (EditText) findViewById(R.id.add_todo_description_et);
        todo_date_et = (EditText) findViewById(R.id.add_todo_date_et);
        todo_category_sp = (Spinner) findViewById(R.id.add_todo_category_sp);
        todo_priority_sp = (Spinner) findViewById(R.id.add_todo_priority_sp);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn = (Button) findViewById(R.id.ok_btn);

        try {
            final Dao<Category, Integer> categoryDAO = getHelper().getCategoryDAO();
            final Dao<Priority, Integer> priorityDAO = getHelper().getPriorityDAO();

            categories = categoryDAO.queryForAll();
            priorities = priorityDAO.queryForAll();

            todo_category_sp.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categories));
            todo_priority_sp.setAdapter(new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, priorities));
        } catch (SQLException ex) {
            Log.e(LOG, "cannot get todoDAO", ex);
        }

        cancel_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ok_btn) {
            final Todo todo = new Todo();
            todo.setTitle(todo_title_et.getText().toString());
            todo.setDescription(todo_description_et.getText().toString());
            todo.setPriority(new Priority());


            try {
                final Dao<Todo, Integer> todoDao = getHelper().getTodoDAO();

                todoDao.create(todo);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } catch (SQLException ex) {
                Log.e(LOG,"error by creating todo",ex);
            }
        }
    }

    public OrmDbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }
}
