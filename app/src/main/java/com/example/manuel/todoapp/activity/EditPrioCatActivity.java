package com.example.manuel.todoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.manuel.todoapp.model.Category;
import com.example.manuel.todoapp.util.OrmDbHelper;
import com.example.manuel.todoapp.R;
import com.example.manuel.todoapp.model.Priority;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by Manuel on 26.02.2018.
 */

public class EditPrioCatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG = EditPrioCatActivity.class.getName();

    private String activityArt;
    private String caller;
    private OrmDbHelper dbHelper = null;

    private EditText priority_name_et;
    private Button ok_btn;
    private Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityArt = getIntent().getStringExtra(MainActivity.EXTRA_ACTIVITY_ART);
        caller = getIntent().getStringExtra(MainActivity.EXTRA_CALLER);
        setTitle(activityArt);
        setContentView(R.layout.activity_edit_prio_cat);

        priority_name_et = findViewById(R.id.add_priority_name_et);
        ok_btn = findViewById(R.id.add_priority_ok_btn);
        cancel_btn = findViewById(R.id.add_priority_cancel_btn);

        if(activityArt.equals(MainActivity.EXTRA_PRIORITY_EDIT) || activityArt.equals(MainActivity.EXTRA_CATEGORY_EDIT)) {
            cancel_btn.setText("Löschen");
            priority_name_et.setText(getIntent().getStringExtra("name"));
        }

        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ok_btn) {
            if (activityArt.equals(MainActivity.EXTRA_PRIORITY_ADD)) {
                final Priority prio = new Priority();
                prio.setName(priority_name_et.getText().toString());

                try {
                    final Dao<Priority, Integer> prioDAO = getHelper().getPriorityDAO();

                    prioDAO.create(prio);
                    returnLastActivity();

                } catch (SQLException ex) {
                    Log.e(LOG, "error by creating priority", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            } else if (activityArt.equals(MainActivity.EXTRA_CATEGORY_ADD)) {
                final Category category = new Category();
                category.setName(priority_name_et.getText().toString());

                try {
                    final Dao<Category, Integer> categoryDAO = getHelper().getCategoryDAO();

                    categoryDAO.create(category);
                    returnLastActivity();

                } catch (SQLException ex) {
                    Log.e(LOG, "error by creating category", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            } else if (activityArt.equals(MainActivity.EXTRA_PRIORITY_EDIT)) {
                try {
                    final Dao<Priority, Integer> prioDAO = getHelper().getPriorityDAO();
                    Priority toedit = prioDAO.queryForId(getIntent().getIntExtra("id", -1));
                    toedit.setName(priority_name_et.getText().toString());
                    prioDAO.update(toedit);
                    returnLastActivity();
                } catch (SQLException ex) {
                    Log.e(LOG, "error by editing priority", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            } else if (activityArt.equals(MainActivity.EXTRA_CATEGORY_EDIT)) {
                try {
                    final Dao<Category, Integer> categoryDAO = getHelper().getCategoryDAO();
                    Category toedit = categoryDAO.queryForId(getIntent().getIntExtra("id", -1));
                    toedit.setName(priority_name_et.getText().toString());
                    categoryDAO.update(toedit);
                    returnLastActivity();
                } catch (SQLException ex) {
                    Log.e(LOG, "error by editing category", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            }
        } else if (view == cancel_btn) {
            if (activityArt.equals(MainActivity.EXTRA_PRIORITY_ADD) || activityArt.equals(MainActivity.EXTRA_CATEGORY_ADD)) {
                try {
                    returnLastActivity();
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            } else if (activityArt.equals(MainActivity.EXTRA_PRIORITY_EDIT)) {
                try {
                    final Dao<Priority, Integer> prioDAO = getHelper().getPriorityDAO();
                    prioDAO.deleteById(getIntent().getIntExtra("id", -1));
                    returnLastActivity();
                } catch (SQLException ex) {
                    Log.e(LOG, "error by deleting priority", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            } else if (activityArt.equals(MainActivity.EXTRA_CATEGORY_EDIT)) {
                try {
                    final Dao<Category, Integer> categoryDAO = getHelper().getCategoryDAO();
                    categoryDAO.deleteById(getIntent().getIntExtra("id", -1));
                    returnLastActivity();
                } catch (SQLException ex) {
                    Log.e(LOG, "error by deleting category", ex);
                } catch (ClassNotFoundException ex) {
                    Log.e(LOG, "error by returning to last Activity", ex);
                }
            }
        }
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

    private void returnLastActivity() throws ClassNotFoundException {
        Intent last_intent = new Intent(getApplicationContext(),
                Class.forName(caller)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(last_intent);
    }
}
