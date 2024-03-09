package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.Order;
import com.example.lispelDoc2.models.Service;

import java.util.List;
@Dao
public interface OrderDAO {
    @Insert
    Long insert(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllEntities();

    @Query("SELECT * FROM order_table WHERE id = :id")
    LiveData<Order> getEntityById(Long id);
}
