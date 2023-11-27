package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.ComponentDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Component;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class ComponentProxyDAO implements LispelDAO {
    private final ComponentDAO componentDAO;
    private String title;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public ComponentProxyDAO(Application application){
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.componentDAO = lispelRoomDatabase.componentDAO();
        this.title = "";
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return componentDAO.insert((Component) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        if (title.equals("")) {
            return LiveDataListConverter.convert(owner, componentDAO.getAllEntities());
        } else {
            return LiveDataListConverter.convert(owner, componentDAO.getAllEntitiesWithComponentName(title));
        }
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        if (title.equals("")){
            return LiveDataListConverter.convert(owner, componentDAO.getAllEntitiesByName(s));
        } else {
            return LiveDataListConverter.convert(owner, componentDAO.getAllEntitiesByNameWithComponentName(title, s));
        }
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return componentDAO.getEntityById(id);
    }
}
