package com.example.manuel.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Manuel on 23.02.2018.
 */
@DatabaseTable(tableName = "todo_categoriy")
public class TodoCategory {
    @DatabaseField(foreign = true)
    private Todo todo;
    @DatabaseField(foreign = true)
    private Category category;
}
