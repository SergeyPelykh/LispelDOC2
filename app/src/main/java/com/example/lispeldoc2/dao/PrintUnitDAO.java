package com.example.lispeldoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispeldoc2.models.PrintUnit;

import java.util.List;

@Dao
public interface PrintUnitDAO {
    @Insert
    Long insert(PrintUnit printUnit);

    @Query("SELECT * FROM printUnit_table")
    LiveData<List<PrintUnit>> getAllEntities();

    @Query("SELECT * FROM printUnit_table WHERE number = :number")
    LiveData<PrintUnit> getEntityByNumber(String number);
}
