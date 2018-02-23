package com.example.manuel.todoapp;

/**
 * Created by Alexander on 13.02.2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG = OrmDbHelper.class.getName();
    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 1;

    private Dao<Todo, Integer> todoDAO = null;
    private Dao<TodoCategory, Integer> todoCategoryDAO = null;
    private Dao<Category, Integer> categoryDAO = null;
    private Dao<Priority, Integer> priorityDAO = null;

    public OrmDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource source) {
        try {
            TableUtils.createTable(source, Todo.class);
            TableUtils.createTable(source, Category.class);
            TableUtils.createTable(source, Priority.class);
            TableUtils.createTable(source, TodoCategory.class);
        } catch (SQLException ex) {
            Log.e(LOG, "error creating tables", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, Todo.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Priority.class, true);
            TableUtils.dropTable(connectionSource, TodoCategory.class, true);
            onCreate(database, connectionSource);

        } catch (SQLException ex) {
            Log.e(LOG, "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, ex);
        }
    }

    public Dao<Todo, Integer> getTodoDAO() throws SQLException {
        if (todoDAO == null) {
            todoDAO = getDao(Todo.class);
        }
        return todoDAO;
    }

    public Dao<Category, Integer> getCategoryDAO() throws SQLException {
        if(categoryDAO == null) {
            categoryDAO = getDao(Category.class);
        }
        return categoryDAO;
    }

    public Dao<Priority, Integer> getPriorityDAO() throws SQLException {
        if (priorityDAO == null) {
            priorityDAO = getDao(Priority.class);
        }
        return priorityDAO;
    }

    public Dao<TodoCategory, Integer> getTodoCategoryDAO() throws  SQLException {
        if (todoCategoryDAO == null){
            todoCategoryDAO = getDao(TodoCategory.class);
        }
        return todoCategoryDAO;
    }
}
