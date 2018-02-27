package com.example.manuel.todoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import java.util.List;

/**
 * Created by Manuel on 27.02.2018.
 */

public class ViewCategoryActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemClickListener{

    private List<Category> categories;
    private Dao<Category, Integer> categoryDAO;
    private OrmDbHelper dbHelper = null;

    private FloatingActionButton fab;
    private ListView categoryLV;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Kategorien");
        setContentView(R.layout.activity_view_category);

        fab = findViewById(R.id.view_category_fab);
        categoryLV = findViewById(R.id.category_lv);

        fab.setOnClickListener(this);
        categoryLV.setOnItemClickListener(this);

        try {
            categoryDAO = getHelper().getCategoryDAO();
            populateListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == fab){
            Intent intent = new Intent(this, EditPrioCatActivity.class);
            intent.putExtra(MainActivity.EXTRA_ACTIVITY_ART, MainActivity.EXTRA_CATEGORY_ADD);
            intent.putExtra(MainActivity.EXTRA_CALLER, getIntent().getComponent().getClassName());
            startActivity(intent);
        }
    }

    public void populateListView() throws SQLException {
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.view_priority_entry, categoryLV, false);
        categoryLV.addHeaderView(rowView);

        CloseableIterator<Category> iterator = categoryDAO.closeableIterator();

        // get the raw results which can be cast under Android
        AndroidDatabaseResults results = (AndroidDatabaseResults) iterator.getRawResults();
        Cursor cursor = results.getRawCursor();
        String[] from = new String[]{"_id", "name"};
        int[] to = new int[]{R.id.view_category_id,R.id.view_category_name};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.view_category_entry, cursor, from, to,0);

        categoryLV.setAdapter(sca);
    }

    private OrmDbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return dbHelper;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView categoryID = (TextView) view.findViewById(R.id.view_category_id);
        TextView categoryName = (TextView) view.findViewById(R.id.view_category_name);

        int categoryID_val = Integer.parseInt(categoryID.getText().toString());
        String categoryName_val = categoryName.getText().toString();

        Intent modify_intent = new Intent(getApplicationContext(),EditPrioCatActivity.class);
        modify_intent.putExtra(MainActivity.EXTRA_ACTIVITY_ART, MainActivity.EXTRA_CATEGORY_EDIT);
        modify_intent.putExtra(MainActivity.EXTRA_CALLER, getIntent().getComponent().getClassName());
        modify_intent.putExtra("id", categoryID_val);
        modify_intent.putExtra("name", categoryName_val);
        startActivity(modify_intent);
    }
}
