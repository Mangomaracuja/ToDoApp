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

    public TodoCategory() {

    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
