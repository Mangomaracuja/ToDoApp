//package com.example.manuel.todoapp;
//
///**
// * Created by Alexander on 13.02.2018.
// */
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//import android.widget.ListView;
//
//public class DbHelperAlt extends SQLiteOpenHelper {
//
//    private static OrmDbHelper instance;
//
//    public static final String LOG_TAG = OrmDbHelper.class.getName();
//
//    public static final String DATABASE_NAME = "grouptodo.db";
//    public static final int DATABASE_VERSION = 1;
//
//    public static final String TODO_TABLE_NAME = "todo";
//    public static final String TODO_ID_FIELD_NAME = "_id";
//    public static final String TODO_ID_FIELD_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
//    public static final String TODO_DATE_FIELD_NAME = "date";
//    public static final String TODO_DATE_FIELD_TYPE = "INTEGER";
//    public static final String TODO_TITLE_FIELD_NAME = "title";
//    public static final String TODO_TITLE_FIELD_TYPE = "TEXT";
//    public static final String TODO_DESCR_FIELD_NAME = "description";
//    public static final String TODO_DESCR_FIELD_TYPE = "TEXT";
//    public static final String TODO_PRIO_FIELD_NAME = "priority";
//    public static final String TODO_PRIO_FIELD_TYPE = "TEXT";
//    public static final String TODO_PARTEN_FIELD_NAME= "parent";
//    public static final String TODO_PARTEN_FIELD_TYPE= "INTEGER";
//    public static final String TODO_PARTEN_FOREIGN_KEY= "CONSTRAINT"+
//            "fk_parent FOREIGN KEY (parent REFERENCES todoGroup(_id))";
//
//    public static final String TODO_GROUP_TABLE_NAME = "todo_group";
//    public static final String TODO_GROUP_ID_FIELD_NAME ="_id";
//    public static final String TODO_GROUP_ID_FIELD_TYPE ="INTEGER PRIMARY KEY AUTOINCREMENT";
//    public static final String TODO_GROUP_NAME_FIELD_NAME = "name";
//    public static final String TODO_GROUP_NAME_FIELD_TYPE = "TEXT";
//
//
//    private OrmDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public static OrmDbHelper getInstance(Context context) {
//        if (instance == null) {
//            instance = new OrmDbHelper(context);
//        }
//        return instance;
//    }
//
//    ListView lstTask;
//    // SQL statement zum Erstellen der Tabelle
//    public static final String TODO_TABLE_CREATE =
//            "CREATE TABLE " + TODO_TABLE_NAME + "("
//                    + TODO_ID_FIELD_NAME      + " " + TODO_ID_FIELD_TYPE     + ", "
//                    + TODO_TITLE_FIELD_NAME   + " " + TODO_TITLE_FIELD_TYPE  + ", "
//                    + TODO_DATE_FIELD_NAME    + " " + TODO_DATE_FIELD_TYPE   + ", "
//                    + TODO_PRIO_FIELD_NAME    + " " + TODO_PRIO_FIELD_TYPE   + ", "
//                    + TODO_DESCR_FIELD_NAME   + " " + TODO_DESCR_FIELD_TYPE  + ", "
//                    + TODO_PARTEN_FIELD_NAME  + " " + TODO_PARTEN_FIELD_TYPE + ", "
//                    + TODO_PARTEN_FOREIGN_KEY + ")";
//
//    public static final String TODO_GROUP_TABLE_CREATE =
//            String.format("CREATE TABLE %s (%s %s, %s %s)", TODO_GROUP_TABLE_NAME,
//                    TODO_GROUP_ID_FIELD_NAME, TODO_GROUP_ID_FIELD_TYPE,
//                    TODO_GROUP_NAME_FIELD_NAME, TODO_GROUP_NAME_FIELD_TYPE);
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        try {
//            db.execSQL(TODO_TABLE_CREATE);
//            Log.i("TableCreation", "Todo Table erstellt");
//            db.execSQL(TODO_GROUP_TABLE_CREATE);
//            Log.i("TableCreation", "Group Table erstellt");
//        } catch (SQLException ex) {
//            Log.e("DatabaseHelper", "error creating tables", ex);
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String query = String.format("DELETE TABLE IF EXISTS %s", TODO_TABLE_NAME);
//        db.execSQL(query);
//        onCreate(db);
//    }
//
//
//    /**
//     * Einfuegen eines Todos mit notwendigen Inhalten
//     *
//     * @param title       Titel
//     * @param description Beschreibeung
//     * @param priority    Priorität
//     */
//    public void insertNewTask(String title, String description, String priority) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(OrmDbHelper.TODO_ID_FIELD_NAME, title);
//        values.put(OrmDbHelper.TODO_DESCR_FIELD_NAME, description);
//        values.put(OrmDbHelper.TODO_PRIO_FIELD_NAME, priority);
//        // Datensatz in die Datenbank einfügen
//        db.insert(OrmDbHelper.TODO_TABLE_NAME, null, values);
//        Log.i("test", title + description);
//        // Datenbank schließen
//        db.close();
//    }
//
//
//    /**
//     * Löschen eines Todo's
//     *
//     * @param title wird zum loeschen uebergeben
//     */
//    public void deleteTask(String title) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(OrmDbHelper.TODO_TABLE_NAME,
//                OrmDbHelper.TODO_DESCR_FIELD_NAME + " = " + title, null);
//        //db.delete(DB_TABLE,TODO_TABLE_NAME + " = ?",new String[]{title,null, null});
//        db.close();
//    }
//
//
//
//}
