package com.example.manuel.todoapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by manuel on 20.02.18.
 */

@DatabaseTable(tableName="todo")
public class Todo {

    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField
    private long date;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField(canBeNull = false,foreign = true, foreignAutoRefresh = true)
    private Priority priority;

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
}