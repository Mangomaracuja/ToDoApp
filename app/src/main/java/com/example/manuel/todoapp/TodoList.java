package com.example.manuel.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by manuel on 20.02.18.
 */

@DatabaseTable(tableName="todolist")
public class TodoList {
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField
    private String name;
    @ForeignCollectionField
    private Collection<Todo> todos;
    public TodoList() {
    }

}
