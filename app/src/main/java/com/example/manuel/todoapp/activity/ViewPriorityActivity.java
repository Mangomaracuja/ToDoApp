package com.example.manuel.todoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.manuel.todoapp.util.OrmDbHelper;
import com.example.manuel.todoapp.R;
import com.example.manuel.todoapp.model.Priority;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Manuel on 27.02.2018.
 */

public class ViewPriorityActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private static final String LOG = ViewPriorityActivity.class.getName();

    private List<Priority> priorities;
    private Dao<Priority, Integer> prioDAO;
    private OrmDbHelper dbHelper = null;

    private FloatingActionButton fab;
    private ListView prioLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Prioritäten");
        setContentView(R.layout.activity_view_priority);

        fab = findViewById(R.id.view_prio_fab);
        prioLv = findViewById(R.id.priority_lv);

        fab.setOnClickListener(this);
        prioLv.setOnItemClickListener(this);

        try {
            prioDAO = getHelper().getPriorityDAO();
            populateListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
            Intent intent = new Intent(this, EditPrioCatActivity.class);
            intent.putExtra(MainActivity.EXTRA_ACTIVITY_ART, MainActivity.EXTRA_PRIORITY_ADD);
            intent.putExtra(MainActivity.EXTRA_CALLER, getIntent().getComponent().getClassName());
            startActivity(intent);
        }
    }

    public void populateListView() throws SQLException {

        priorities = prioDAO.queryForAll();

        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_priority_entry, prioLv, false);
        prioLv.addHeaderView(rowView);

        CloseableIterator<Priority> iterator = prioDAO.closeableIterator();

        // get the raw results which can be cast under Android
        AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
        Cursor cursor = results.getRawCursor();
        String[] from = new String[]{"_id", "name"};
        int[] to = new int[]{R.id.view_priority_id, R.id.view_priority_name};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.view_priority_entry, cursor, from, to, 0);

        prioLv.setAdapter(sca);
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
            TextView prioID = (TextView) view.findViewById(R.id.view_priority_id);
            TextView prioName = (TextView) view.findViewById(R.id.view_priority_name);

            int prioID_val = Integer.parseInt(prioID.getText().toString());
            String prioName_val = prioName.getText().toString();

            Intent modify_intent = new Intent(getApplicationContext(), EditPrioCatActivity.class);
            modify_intent.putExtra(MainActivity.EXTRA_ACTIVITY_ART, MainActivity.EXTRA_PRIORITY_EDIT);
            modify_intent.putExtra(MainActivity.EXTRA_CALLER, getIntent().getComponent().getClassName());
            modify_intent.putExtra("id", prioID_val);
            modify_intent.putExtra("name", prioName_val);
            startActivity(modify_intent);
        } catch (NumberFormatException ex) {
            Log.e(LOG, "error by selection", ex);
        }
    }
}
