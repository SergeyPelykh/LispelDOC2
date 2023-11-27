package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.Component;

import java.util.List;

@Dao
public interface ComponentDAO {
    @Insert
    Long insert(Component component);

    @Query("SELECT * FROM component_table WHERE componentName =:componentName")
    LiveData<List<Component>> getAllEntitiesWithComponentName(String componentName);

    @Query("SELECT * FROM component_table WHERE aliasName LIKE :aliasName AND componentName =:componentName")
    LiveData<Component>getEntityByNameWithComponentName(String componentName, String aliasName);

    @Query("SELECT * FROM component_table WHERE aliasName LIKE :aliasName AND componentName =:componentName")
    LiveData<List<Component>> getAllEntitiesByNameWithComponentName(String componentName, String aliasName);

    @Query("SELECT * FROM component_table")
    LiveData<List<Component>> getAllEntities();

    @Query("SELECT * FROM component_table WHERE aliasName LIKE :aliasName")
    LiveData<Component>getEntityByName(String aliasName);

    @Query("SELECT * FROM component_table WHERE aliasName LIKE :aliasName")
    LiveData<List<Component>> getAllEntitiesByName(String aliasName);

    @Query("SELECT * FROM component_table WHERE id = :id")
    LiveData<Component> getEntityById(Long id);
}
