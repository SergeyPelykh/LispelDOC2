package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.ServiceDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Service;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class ServiceProxyDAO implements LispelDAO {
    private final ServiceDAO serviceDAO;

    public ServiceProxyDAO(Application application) {
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.serviceDAO = lispelRoomDatabase.serviceDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return serviceDAO.insert((Service) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, serviceDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, serviceDAO.getAllEntitiesByOrderUnitSticker(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return serviceDAO.getEntityById(id);
    }
}
