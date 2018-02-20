package com.example.manuel.todoapp;

/**
 * Created by Alexander on 13.02.2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import static com.example.manuel.todoapp.DbHelperAlt.DATABASE_NAME;
import static com.example.manuel.todoapp.DbHelperAlt.DATABASE_VERSION;

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    private static OrmDbHelper instance;

    public static final String LOG = OrmDbHelper.class.getName();
    public static final String DB_NAME = "todo.db";
    public static final int DB_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource source) {
        try {
            TableUtils.createTable(source, Todo.class);
            TableUtils.createTable(source, TodoList.class);
        } catch (SQLException ex) {
            Log.e(LOG, "error creating tables", ex);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                updateFromVersion1(database, connectionSource, oldVersion, newVersion);
                break;
            case 2:
                updateFromVersion2(database, connectionSource, oldVersion, newVersion);
                break;
            default:
                // no updates needed
                break;
        }
    }

    private void updateFromVersion1(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // do some stuff here
        onUpgrade(database, connectionSource, oldVersion + 1, newVersion);
    }

    private void updateFromVersion2(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // do some stuff here
        onUpgrade(database, connectionSource, oldVersion + 1, newVersion);
    }

    private OrmDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static OrmDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new OrmDbHelper(context);
        }
        return instance;
    }

    public Dao<Todo, Integer> createTodoDAO() {
        try {
            return DaoManager.createDao(connectionSource, Todo.class);
        } catch (SQLException ex) {
            Log.e(LOG, "error creating DAO for Todo class", ex);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
