package com.example.lispelDoc2.dao.proxyDAO;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispelDoc2.dao.ClientDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.LispelDAO;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.Client;
import com.example.lispelDoc2.utilities.LiveDataListConverter;

import java.util.List;

public class ClientProxyDAO implements LispelDAO {
    private final ClientDAO clientDAO;

    public ClientProxyDAO(Application application) {
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.clientDAO = lispelRoomDatabase.clientDAO();
    }
    @Override
    public Long insert(SavingObject savingObject) {
        return clientDAO.insert((Client) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        return LiveDataListConverter.convert(owner, clientDAO.getAllEntities());
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s) {
        return LiveDataListConverter.convert(owner, clientDAO.getAllEntitiesByName(s));
    }

    @Override
    public LiveData<? extends SavingObject> getEntityById(Long id) {
        return clientDAO.getEntityById(id);
    }
}
