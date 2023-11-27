package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.OrderUnit;
import com.example.lispelDoc2.models.StringValue;

import java.util.List;

@Dao
public interface OrderUnitDAO {
    @Insert
    Long insert(OrderUnit orderUnit);

    @Query("SELECT * FROM orderUnit_table")
    LiveData<List<OrderUnit>> getAllEntities();

    @Query("SELECT * FROM orderUnit_table WHERE stickerNumber = :stickerNumber")
    LiveData<OrderUnit>getEntityByStickerNumber(String stickerNumber);

    @Query("SELECT * FROM orderUnit_table WHERE stickerNumber LIKE :stickerNumber")
    LiveData<List<OrderUnit>> getAllEntitiesByStickerNumber(String stickerNumber);

    @Query("SELECT * FROM orderUnit_table WHERE id = :id")
    LiveData<OrderUnit> getEntityById(Long id);
}
