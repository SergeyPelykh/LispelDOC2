package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.StringValueDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.StringValue;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class StringValueProxyDAO implements LispelDAO {
    private final StringValueDAO stringValueDAO;
    private String title;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public StringValueProxyDAO(Application application){
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.stringValueDAO = lispelRoomDatabase.stringValueDAO();
    }
    @Override
    public Long insert(SavingObject savingObject) {
        return stringValueDAO.insert((StringValue) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, stringValueDAO.getAllEntities(title));
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, stringValueDAO.getAllEntitiesByName(title, s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return stringValueDAO.getEntityById(title, id);
    }
}
