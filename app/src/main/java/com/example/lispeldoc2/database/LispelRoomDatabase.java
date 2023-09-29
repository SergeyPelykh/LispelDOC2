package com.example.lispeldoc2.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lispeldoc2.dao.FieldDAO;
import com.example.lispeldoc2.dao.PrintUnitDAO;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        PrintUnit.class,
        Field.class},
        version = 1, exportSchema = false)
public abstract class LispelRoomDatabase extends RoomDatabase {
    public abstract PrintUnitDAO printUnitDAO();
    public abstract FieldDAO fieldDAO();
    private static volatile LispelRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.
            newFixedThreadPool(NUMBER_OF_THREADS);

    public static LispelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null){
            synchronized (LispelRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LispelRoomDatabase.class,
                            "roomDatabase").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
