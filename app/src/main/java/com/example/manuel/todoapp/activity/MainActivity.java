package com.example.manuel.todoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.manuel.todoapp.util.OrmDbHelper;
import com.example.manuel.todoapp.R;
import com.example.manuel.todoapp.model.Todo;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {

    public static final String EXTRA_ACTIVITY_ART = "com.example.manuel.todoapp.ACTIVITY_ART";
    public static final String EXTRA_CALLER = "callingActivity";
    public static final String EXTRA_TODO_ADD = "ToDo Hinzufügen";
    public static final String EXTRA_TODO_EDIT = "ToDo Bearbeiten";
    public static final String EXTRA_CATEGORY_ADD = "Kategorie Hinzufügen";
    public static final String EXTRA_CATEGORY_EDIT = "Kategorie Bearbeiten";
    public static final String EXTRA_PRIORITY_ADD = "Priorität Hinzufügen";
    public static final String EXTRA_PRIORITY_EDIT = "Priorität Bearbeiten";

    private static final String LOG = MainActivity.class.getName();

    private OrmDbHelper dbHelper = null;
    private List<Todo> todos;
    private Dao<Todo, Integer> todoDAO;

    private ArrayAdapter<String> mAdapter;
    private ListView myList;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.view_todo_fab);
        fab.setOnClickListener(this);

        myList = (ListView) findViewById(R.id.todo_lv);
        myList.setOnItemClickListener(this);

        try {
            todoDAO = getHelper().getTodoDAO();
            populateListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, EditTodoActivity.class);
            intent.putExtra(EXTRA_ACTIVITY_ART, EXTRA_TODO_ADD);
            startActivity(intent);
        }
    }


    /**
     * Funktion zum Ausgeben der TODOS
     */
    public void populateListView() throws SQLException {
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_todo_entry, myList, false);
        myList.addHeaderView(rowView);

        CloseableIterator<Todo> iterator = todoDAO.closeableIterator();
        // get the raw results which can be cast under Android
        AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();


        Cursor cursor = results.getRawCursor();
        String[] from = new String[]{Todo.ID, Todo.TITLE, Todo.DESCRIPTION};
        int[] to = new int[]{R.id.view_todo_id, R.id.view_todo_title, R.id.view_todo_date};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.view_todo_entry, cursor, from, to, 0);

        myList.setAdapter(sca);

    }


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_edit_priority:
                intent = new Intent(this, ViewPriorityActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_edit_category:
                intent = new Intent(this, ViewCategoryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private OrmDbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            TextView todoID = (TextView) view.findViewById(R.id.view_todo_id);

            int todoID_val = Integer.parseInt(todoID.getText().toString());

            Intent modify_intent = new Intent(getApplicationContext(), EditTodoActivity.class);
            modify_intent.putExtra(EXTRA_ACTIVITY_ART, EXTRA_TODO_EDIT);
            modify_intent.putExtra(Todo.ID, todoID_val);
            startActivity(modify_intent);
        } catch (NumberFormatException ex) {
            Log.e(LOG, "error by selection", ex);
        }
    }
}
