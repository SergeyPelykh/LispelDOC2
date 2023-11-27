package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.PrintUnitDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.PrintUnit;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class PrintUnitProxyDAO implements LispelDAO {

    private final PrintUnitDAO printUnitDAO;

    public PrintUnitProxyDAO(Application application){
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.printUnitDAO = lispelRoomDatabase.printUnitDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return printUnitDAO.insert((PrintUnit) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, printUnitDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, printUnitDAO.getAllEntitiesByModel(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return printUnitDAO.getEntityById(id);
    }
}
