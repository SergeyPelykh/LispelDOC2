package com.example.lispelDoc2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lispelDoc2.models.Sticker;

import java.util.List;
@Dao
public interface StickerDAO {
    @Insert
    Long insert(Sticker sticker);

    @Query("SELECT * FROM sticker_table")
    LiveData<List<Sticker>> getAllEntities();

    @Query("SELECT * FROM sticker_table WHERE number LIKE :number")
    LiveData<Sticker>getEntityByName(String number);

    @Query("SELECT * FROM sticker_table WHERE number LIKE :number")
    LiveData<List<Sticker>> getAllEntitiesByName(String number);

    @Query("SELECT * FROM sticker_table WHERE id = :id")
    LiveData<Sticker> getEntityById(Long id);

    @Query("SELECT * FROM sticker_table ORDER BY dateOfCreate DESC LIMIT 1")
    LiveData<Sticker> getLastEntity();
}
