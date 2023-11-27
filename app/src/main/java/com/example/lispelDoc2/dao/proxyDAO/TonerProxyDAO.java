package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.TonerDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Toner;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class TonerProxyDAO implements LispelDAO {
    private final TonerDAO tonerDAO;

    public TonerProxyDAO(Application application) {
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.tonerDAO = lispelRoomDatabase.tonerDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return tonerDAO.insert((Toner) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, tonerDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, tonerDAO.getAllEntitiesByName(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return tonerDAO.getEntityById(id);
    }
}
