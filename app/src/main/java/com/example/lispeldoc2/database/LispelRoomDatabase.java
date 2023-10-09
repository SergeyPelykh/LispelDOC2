package com.example.lispeldoc2.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.lispeldoc2.dao.FieldDAO;
import com.example.lispeldoc2.dao.PrintUnitDAO;
import com.example.lispeldoc2.dao.StringValueDAO;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.models.StringValue;
import com.example.lispeldoc2.utilities.Convert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        PrintUnit.class,
        StringValue.class},
        version = 1, exportSchema = false)
@TypeConverters({Convert.class})
public abstract class LispelRoomDatabase extends RoomDatabase {
    public abstract PrintUnitDAO printUnitDAO();
    public abstract StringValueDAO stringValueDAO();
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
