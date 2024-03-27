package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.lispelDoc2.models.Component;
import com.example.lispelDoc2.models.PrintUnit;
import com.example.lispelDoc2.utilities.Convert;


import java.util.List;

@Dao
public interface ComponentDAO {
    @Query("DELETE FROM component_table")
    void deleteAll();

    @Query("SELECT COUNT(id) FROM component_table")
    int getCountEntities();

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



    @TypeConverters(Convert.class)
    @Query(" SELECT * FROM component_table WHERE compatibility LIKE '%' || (SELECT model FROM printUnit_table WHERE model IN (SELECT printUnitName FROM orderUnit_table WHERE stickerNumber = :stickerNumber )) || '%'")
    //@Query(" SELECT * FROM component_table")
    LiveData<List<Component>> getEntityByCompatibility(String stickerNumber);
    //LiveData<List<Component>> getEntityByCompatibility();
}
