package com.example.lispeldoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispeldoc2.models.Field;

import java.util.List;


public interface FieldDAO {
    @Insert
    Long insert(Field field);

    @Query("SELECT * FROM field_table")
    LiveData<List<Field>>getAllEntities();

    @Query("SELECT * FROM field_table WHERE name = :name")
    LiveData<Field> getEntityByName (String name);


}
