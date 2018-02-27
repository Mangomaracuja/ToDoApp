package com.example.manuel.todoapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Manuel on 22.02.2018.
 */
@DatabaseTable(tableName = "priority")
public class Priority {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;
    @DatabaseField
    private String name;

    public Priority() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
