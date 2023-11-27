package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.Service;
import com.example.lispelDoc2.models.Sticker;

import java.util.List;

@Dao
public interface ServiceDAO {
    @Insert
    Long insert(Service service);

    @Query("SELECT * FROM service_table")
    LiveData<List<Service>> getAllEntities();

    @Query("SELECT * FROM service_table WHERE orderUnitSticker LIKE :orderUnitSticker")
    LiveData<Service>getEntityByOrderUnitSticker(String orderUnitSticker);

    @Query("SELECT * FROM service_table WHERE orderUnitSticker LIKE :orderUnitSticker")
    LiveData<List<Service>> getAllEntitiesByOrderUnitSticker(String orderUnitSticker);

    @Query("SELECT * FROM service_table WHERE id = :id")
    LiveData<Service> getEntityById(Long id);
}
