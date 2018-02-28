package com.example.manuel.todoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.manuel.todoapp.R;
import com.example.manuel.todoapp.model.Category;
import com.example.manuel.todoapp.util.OrmDbHelper;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuel on 28.02.18.
 */

public class ChooseCategoryActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private List<Category> categories;
    private Dao<Category, Integer> categoryDAO;
    private OrmDbHelper dbHelper = null;

    private FloatingActionButton fab;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        fab = findViewById(R.id.category_choose_fab);
        lv = findViewById(R.id.category_choose_lv);

        fab.setOnClickListener(this);
        lv.setOnItemClickListener(this);

        try {
            categoryDAO = getHelper().getCategoryDAO();
            populateListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
            int count = lv.getCount();
            SparseBooleanArray spa = lv.getCheckedItemPositions();
            ArrayList<Integer> ids = new ArrayList<>();
            View v;
            TextView tv;
            for(int i = 0; i < count; i++){
                if(spa.get(i)) {
                    v = lv.getChildAt(i);
                    tv = v.findViewById(R.id.view_category_choose_id);
                    ids.add(Integer.parseInt(tv.getText().toString()));
                }
            }
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",ids);
            setResult(MainActivity.RESULT_OK,returnIntent);
            finish();
        }
    }

    public void populateListView() throws SQLException {
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_priority_entry, lv, false);
        lv.addHeaderView(rowView);

        CloseableIterator<Category> iterator = categoryDAO.closeableIterator();

        // get the raw results which can be cast under Android
        AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
        Cursor cursor = results.getRawCursor();
        String[] from = new String[]{"_id", "name"};
        int[] to = new int[]{R.id.view_category_choose_id,R.id.view_category_choose_name};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.view_category_choose_entry, cursor, from, to,0);

        lv.setAdapter(sca);
    }


    private OrmDbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox cb = view.findViewById(R.id.view_category_choose_check);
        if(cb.isChecked())cb.setChecked(false);
        else cb.setChecked(true);
    }
}