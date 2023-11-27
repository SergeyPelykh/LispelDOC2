package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.OrderUnitDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.OrderUnit;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class OrderUnitProxyDAO implements LispelDAO {
    private final OrderUnitDAO orderUnitDAO;

    public OrderUnitProxyDAO(Application application){
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.orderUnitDAO = lispelRoomDatabase.orderUnitDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return orderUnitDAO.insert((OrderUnit) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, orderUnitDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, orderUnitDAO.getAllEntitiesByStickerNumber(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return orderUnitDAO.getEntityById(id);
    }
}
