package com.example.lispeldoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "string_value_table")
public class StringValue {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    private String name;

    public StringValue() {
    }

    public StringValue(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
