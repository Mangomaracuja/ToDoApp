package com.example.manuel.todoapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manuel.todoapp.util.OrmDbHelper;
import com.example.manuel.todoapp.R;
import com.example.manuel.todoapp.model.Category;
import com.example.manuel.todoapp.model.Priority;
import com.example.manuel.todoapp.model.Todo;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Manuel on 22.02.2018.
 */

public class EditTodoActivity extends AppCompatActivity implements OnClickListener {

    private static final String LOG = EditTodoActivity.class.getName();

    private OrmDbHelper dbHelper = null;
    private String activityArt;
    private Todo toEdit = null;

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
        Intent intent = getIntent();
        activityArt = intent.getStringExtra(MainActivity.EXTRA_ACTIVITY_ART);
        setTitle(activityArt);
        setContentView(R.layout.activity_edit_todo);

        todo_title_et = (EditText) findViewById(R.id.add_todo_title_et);
        todo_description_et = (EditText) findViewById(R.id.add_todo_description_et);
        todo_date_et = (EditText) findViewById(R.id.add_todo_date_et);
        todo_category_sp = (Spinner) findViewById(R.id.add_todo_category_sp);
        todo_priority_sp = (Spinner) findViewById(R.id.add_todo_priority_sp);
        cancel_btn = (Button) findViewById(R.id.add_todo_cancel_btn);
        ok_btn = (Button) findViewById(R.id.add_todo_ok_btn);

        if (activityArt.equals(MainActivity.EXTRA_TODO_EDIT)) cancel_btn.setText("Löschen");



        try {
            final Dao<Category, Integer> categoryDAO = getHelper().getCategoryDAO();
            final Dao<Priority, Integer> priorityDAO = getHelper().getPriorityDAO();

            categories = categoryDAO.queryForAll();
            priorities = priorityDAO.queryForAll();


            todo_category_sp.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categories));
            todo_priority_sp.setAdapter(new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, priorities));
            if(activityArt.equals(MainActivity.EXTRA_TODO_EDIT)){
                toEdit = getHelper().getTodoDAO().queryForId(intent.getIntExtra(Todo.ID,-1));
            }
        } catch (SQLException ex) {
            Log.e(LOG, "cannot get todoDAO", ex);
        }

        if(activityArt.equals(MainActivity.EXTRA_TODO_EDIT) && toEdit != null){
            todo_title_et.setText(toEdit.getTitle());
            todo_description_et.setText(toEdit.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            todo_date_et.setText(sdf.format(new Date(toEdit.getDate())));
            todo_priority_sp.setSelection(priorities.indexOf(toEdit.getPriority()));
            //todo_category_sp.setSelection(categories.indexOf(toEdit));
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
            if (activityArt.equals(MainActivity.EXTRA_TODO_ADD)) {
                toEdit = new Todo();
                toEdit.setTitle(todo_title_et.getText().toString());
                toEdit.setDescription(todo_description_et.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    toEdit.setDate(sdf.parse(todo_date_et.getText().toString()).getTime());
                } catch (ParseException ex) {
                    Toast.makeText(this, "Datum ungültig!",Toast.LENGTH_SHORT).show();
                    Log.e(LOG, "error by parsing date", ex);
                    return;
                }

                toEdit.setPriority((Priority) todo_priority_sp.getSelectedItem());


                try {
                    final Dao<Todo, Integer> todoDao = getHelper().getTodoDAO();

                    todoDao.create(toEdit);
                    returnHome();

                } catch (SQLException ex) {
                    Log.e(LOG, "error by creating todo", ex);
                }
            }else if(activityArt.equals(MainActivity.EXTRA_TODO_EDIT)) {
                toEdit.setTitle(todo_title_et.getText().toString());
                toEdit.setDescription(todo_description_et.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    toEdit.setDate(sdf.parse(todo_date_et.getText().toString()).getTime());
                } catch (ParseException ex) {
                    Toast.makeText(this, "Datum ungültig!",Toast.LENGTH_SHORT).show();
                    Log.e(LOG, "error by parsing date", ex);
                    return;
                }

                toEdit.setPriority((Priority) todo_priority_sp.getSelectedItem());

                try {
                    final Dao<Todo, Integer> todoDao = getHelper().getTodoDAO();

                    todoDao.update(toEdit);
                    returnHome();

                } catch (SQLException ex) {
                    Log.e(LOG, "error by updateing todo", ex);
                }
            }
        } else if (v == cancel_btn) {
            if (activityArt.equals(MainActivity.EXTRA_TODO_EDIT)) {
                try {
                    final Dao<Todo, Integer> todoDao = getHelper().getTodoDAO();
                    todoDao.deleteById(getIntent().getIntExtra("id", -1));
                    returnHome();
                } catch (SQLException ex) {
                    Log.e(LOG, "error by deleting todo", ex);
                }
            }else if (activityArt.equals(MainActivity.EXTRA_TODO_ADD)){
                returnHome();
            }
        }
    }

    public void populateSpinnerPrioView() throws SQLException {
        Dao<Priority, Integer> prioDAO = getHelper().getPriorityDAO();
        List<Priority> priorities = prioDAO.queryForAll();


        CloseableIterator<Priority> iterator = prioDAO.closeableIterator();

        // get the raw results which can be cast under Android
        AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
        Cursor cursor = results.getRawCursor();
        String[] from = new String[]{"_id", "name"};
        int[] to = new int[]{R.id.view_priority_id, R.id.view_priority_name};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.view_priority_entry, cursor, from, to, 0);

        todo_priority_sp.setAdapter(sca);
    }

    public OrmDbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }

    private void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}
