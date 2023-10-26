package com.example.lispeldoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.models.StringValue;

import java.util.List;

@Dao
public interface StringValueDAO {
    @Insert
    Long insert(StringValue stringValue);

    @Query("SELECT * FROM string_value_table WHERE title = :title")
    LiveData<List<StringValue>> getAllEntities(String title);

    @Query("SELECT * FROM string_value_table WHERE name LIKE :name AND title =:title")
    LiveData<StringValue>getEntityByName(String title, String name);

    @Query("SELECT * FROM string_value_table WHERE name LIKE :name")
    LiveData<List<StringValue>> getAllEntitiesByName(String name);
}
