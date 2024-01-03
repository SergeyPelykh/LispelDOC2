package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lispelDoc2.models.Client;

import java.util.List;

@Dao
public interface ClientDAO {
    @Insert
    Long insert(Client client);

    @Update
    void updateEntity(Client client);

    @Query("SELECT * FROM client_table")
    LiveData<List<Client>> getAllEntities();

    @Query("SELECT * FROM client_table WHERE name = :name")
    LiveData<Client> getEntityByName (String name);

    @Query("SELECT * FROM client_table WHERE id = :id")
    LiveData<Client> getEntityById (Long id);

    @Query("SELECT * FROM client_table WHERE name LIKE :name")
    LiveData<List<Client>> getAllEntitiesByName(String name);
}
