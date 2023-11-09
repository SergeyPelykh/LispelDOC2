package com.example.lispeldoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispeldoc2.interfaces.LispelDAO;
import com.example.lispeldoc2.models.Toner;

import java.util.List;

@Dao
public interface TonerDAO{
    @Insert
    Long insert(Toner toner);

    @Query("SELECT * FROM toner_table")
    LiveData<List<Toner>> getAllEntities();

    @Query("SELECT * FROM toner_table WHERE name = :name")
    LiveData<Toner> getEntityByName (String name);

    @Query("SELECT * FROM toner_table WHERE id = :id")
    LiveData<Toner> getEntityById (Long id);

    @Query("SELECT * FROM toner_table WHERE name LIKE :name")
    LiveData<List<Toner>> getAllEntitiesByName(String name);

    @Query("SELECT * FROM toner_table WHERE fullname = :fullName")
    LiveData<Toner> getEntityByProperty (String fullName);
}
