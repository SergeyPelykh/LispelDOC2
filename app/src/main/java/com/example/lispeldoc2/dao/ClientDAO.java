package com.example.lispeldoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.Client;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;

import java.util.List;

@Dao
public interface ClientDAO {
    @Insert
    Long insert(Client client);

    @Query("SELECT * FROM client_table")
    LiveData<List<Client>> getAllEntities();

    @Query("SELECT * FROM client_table WHERE name = :name")
    LiveData<Client> getEntityByName (String name);

    @Query("SELECT * FROM client_table WHERE id = :id")
    LiveData<Client> getEntityById (Long id);

    @Query("SELECT * FROM client_table WHERE name LIKE :name")
    LiveData<List<Client>> getAllEntitiesByName(String name);
}
