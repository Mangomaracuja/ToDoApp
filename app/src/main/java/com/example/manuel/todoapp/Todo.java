package com.example.manuel.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by manuel on 20.02.18.
 */

@DatabaseTable(tableName="todo")
public class Todo {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private long date;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField(foreign = true)
    private TodoList parent;

    public Todo(){

    }

}