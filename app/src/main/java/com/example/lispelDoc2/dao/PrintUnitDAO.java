package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.PrintUnit;

import java.util.List;

@Dao
public interface PrintUnitDAO {
    @Insert
    Long insert(PrintUnit printUnit);

    @Query("SELECT * FROM printUnit_table")
    LiveData<List<PrintUnit>>getAllEntities();

    @Query("SELECT * FROM printUnit_table WHERE model LIKE :model")
    LiveData<PrintUnit> getEntityByModel(String model);

    @Query("SELECT * FROM printUnit_table WHERE model LIKE :model")
    LiveData<List<PrintUnit>> getAllEntitiesByModel(String model);

    @Query("SELECT * FROM printUnit_table WHERE id = :id")
    LiveData<PrintUnit> getEntityById(Long id);
}
