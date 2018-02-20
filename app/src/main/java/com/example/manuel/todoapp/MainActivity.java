package com.example.manuel.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    OrmDbHelper ormDbHelper;
    ArrayAdapter<String> mAdapter;
    ListView myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ormDbHelper = OrmDbHelper.getInstance(this);
        myList  = (ListView)findViewById(R.id.firstTask);
       populateListView();
    }


    /**
     * Funktion zum Ausgeben der TODOS
     *
     */
    public void populateListView(){
        SQLiteDatabase db = ormDbHelper.getReadableDatabase();
        String sql ="select _id, title from todo";
        Cursor cursor = db.rawQuery(sql,null);
        String[] from = new String [] {"title"};
        int [] to = new int[] {R.id.task_title};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                R.layout.row, cursor, from, to, 0);
        myList.setAdapter(sca);
    }



    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                Intent intent = new Intent(this, DetailTodo.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Löschen eines ausgewählten TODO's
     * @param view
     */
    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String StringTodoDes = String.valueOf(taskTextView.getText());

        // TextView todoDes = (TextView)parent.findViewById(R.id.textDes);
        //String task = String.valueOf(todoDes.getText());


        // TextView todoPrio = (TextView)parent.findViewById(R.id.textPrio);
        //String StringTodoPrio = String.valueOf(todoDes.getText());

        Log.e("String", (String) taskTextView.getText());
        ormDbHelper.deleteTask(StringTodoDes);
        //loadTaskList();
    }
}
