package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.StringValue;

import java.util.List;

@Dao
public interface StringValueDAO {
    @Insert
    Long insert(StringValue stringValue);

    @Query("SELECT * FROM string_value_table WHERE title = :title")
    LiveData<List<StringValue>> getAllEntities(String title);

    @Query("SELECT * FROM string_value_table")
    LiveData<List<StringValue>> getAllEntities();

    @Query("SELECT * FROM string_value_table WHERE name LIKE :name AND title =:title")
    LiveData<StringValue>getEntityByName(String title, String name);

    @Query("SELECT * FROM string_value_table WHERE name LIKE :name AND title =:title")
    LiveData<List<StringValue>> getAllEntitiesByName(String title, String name);

    @Query("SELECT * FROM string_value_table WHERE id = :id AND title = :title")
    LiveData<StringValue> getEntityById(String title, Long id);

    @Query("SELECT * FROM string_value_table WHERE id = :id")
    LiveData<StringValue> getEntityById(Long id);
}
