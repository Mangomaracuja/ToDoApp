package com.example.manuel.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by Manuel on 22.02.2018.
 */
@DatabaseTable(tableName = "category")
public class Category {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;
    @DatabaseField
    private String name;
    @ForeignCollectionField
    private Collection<TodoCategory> todos;

    public Category(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
