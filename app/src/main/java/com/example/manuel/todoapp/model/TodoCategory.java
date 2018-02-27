package com.example.manuel.todoapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Manuel on 27.02.2018.
 */
@DatabaseTable(tableName="todo_category")
public class TodoCategory {

    public static final String ID = "_id";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false,foreign = true)
    private Todo todo;
    @DatabaseField(canBeNull = false,foreign = true)
    private Category category;

}
