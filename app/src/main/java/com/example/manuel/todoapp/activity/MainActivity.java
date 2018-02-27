package com.example.manuel.todoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

    public static final String EXTRA_ACTIVITY_ART = "com.example.manuel.todoapp.ACTIVITY_ART";
    public static final String EXTRA_CALLER = "callingActivity";
    public static final String EXTRA_TODO_ADD = "ToDo Hinzufügen";
    public static final String EXTRA_TODO_EDIT = "ToDo Bearbeiten";
    public static final String EXTRA_CATEGORY_ADD = "Kategorie Hinzufügen";
    public static final String EXTRA_CATEGORY_EDIT = "Kategorie Bearbeiten";
    public static final String EXTRA_PRIORITY_ADD = "Priorität Hinzufügen";
    public static final String EXTRA_PRIORITY_EDIT = "Priorität Bearbeiten";

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
        //myList.setOnItemLongClickListener(this);   //for popup menu

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
        String[] from = new String[]{"_id", "title", "date"};
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
                startActivity(intent); break;
            case R.id.menu_edit_category:
                intent = new Intent(this, ViewCategoryActivity.class);
                startActivity(intent); break;
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
    public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
        final PopupMenu popup = new PopupMenu(MainActivity.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_pop_delete) {
                    TextView todoID = (TextView) view.findViewById(R.id.view_todo_id);
                    try {
                        todoDAO.deleteById(Integer.parseInt(todoID.getText().toString()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        popup.show();
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView todoID = (TextView) view.findViewById(R.id.view_todo_id);
        TextView todoTitle = (TextView) view.findViewById(R.id.view_todo_title);
        TextView todoDate = (TextView) view.findViewById(R.id.view_todo_date);

        int todoID_val = Integer.parseInt(todoID.getText().toString());
        String todoTitle_val = todoTitle.getText().toString();
        String todoDate_val = todoDate.getText().toString();

        Intent modify_intent = new Intent(getApplicationContext(), EditTodoActivity.class);
        modify_intent.putExtra(EXTRA_ACTIVITY_ART, EXTRA_TODO_EDIT);
        modify_intent.putExtra(Todo.ID, todoID_val);
        modify_intent.putExtra("title", todoTitle_val);
        modify_intent.putExtra("date", todoDate_val);
        startActivity(modify_intent);
    }
}
