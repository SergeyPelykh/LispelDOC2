package com.example.lispelDoc2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.lispelDoc2.dao.ClientDAO;
import com.example.lispelDoc2.dao.ComponentDAO;
import com.example.lispelDoc2.dao.OrderUnitDAO;
import com.example.lispelDoc2.dao.PrintUnitDAO;
import com.example.lispelDoc2.dao.ServiceDAO;
import com.example.lispelDoc2.dao.StickerDAO;
import com.example.lispelDoc2.dao.StringValueDAO;
import com.example.lispelDoc2.dao.TonerDAO;
import com.example.lispelDoc2.models.Client;
import com.example.lispelDoc2.models.Component;
import com.example.lispelDoc2.models.OrderUnit;
import com.example.lispelDoc2.models.PrintUnit;
import com.example.lispelDoc2.models.Service;
import com.example.lispelDoc2.models.Sticker;
import com.example.lispelDoc2.models.StringValue;
import com.example.lispelDoc2.models.Toner;
import com.example.lispelDoc2.utilities.Convert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {
        PrintUnit.class,
        Client.class,
        StringValue.class,
        Toner.class,
        Component.class,
        Sticker.class,
        OrderUnit.class,
        Service.class},
        version = 6, exportSchema = false)
@TypeConverters({Convert.class})
public abstract class LispelRoomDatabase extends RoomDatabase {
    public abstract PrintUnitDAO printUnitDAO();

    public abstract ClientDAO clientDAO();

    public abstract StringValueDAO stringValueDAO();

    public abstract TonerDAO tonerDAO();

    public abstract ComponentDAO componentDAO();

    public abstract StickerDAO stickerDAO();

    public abstract OrderUnitDAO orderUnitDAO();

    public abstract ServiceDAO serviceDAO();

    private static volatile LispelRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.
            newFixedThreadPool(NUMBER_OF_THREADS);

    public static LispelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LispelRoomDatabase.class) {
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
