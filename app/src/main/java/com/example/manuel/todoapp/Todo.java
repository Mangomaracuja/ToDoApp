package com.example.manuel.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by manuel on 20.02.18.
 */

@DatabaseTable(tableName="todo")
public class Todo {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;
    @DatabaseField
    private long date;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField(canBeNull = false,foreign = true, foreignAutoRefresh = true)
    private Priority priority;
    @ForeignCollectionField
    private Collection<TodoCategory> categories;

    public Todo(){

    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Collection<TodoCategory> getCategories() {
        return categories;
    }

    public void setCategories(Collection<TodoCategory> categories) {
        this.categories = categories;
    }
}