package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.ClientDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.Client;
import com.example.lispeldoc2.models.PrintUnit;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository {
    private ClientDAO clientDAO;

    public ClientRepository(Application application) {
        LispelRoomDatabase lispelRoomDatabase =LispelRoomDatabase.getDatabase(application);
        this.clientDAO = lispelRoomDatabase.clientDAO();
    }

    @Override
    public LiveData<Long> insert(SavingObject savingObject) {
        MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutableLiveData.postValue(clientDAO.insert((Client) savingObject));
            }
        }).start();
        return mutableLiveData;
    }

    @Override
    public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        ArrayList<String> arrayList = new ArrayList();
        clientDAO.getAllEntities().observe(owner, x -> {
            for (Client p : x) {
                arrayList.add(p.getId() + ": " + p.getFullName());
            }
            result.postValue(arrayList);
        });
        return result;
    }


    @Override
    public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        ArrayList<String> arrayList = new ArrayList();
        if (!property.equals("")){
            clientDAO.getAllEntitiesByName("%" + property + "%").observe(owner, x -> {
                for (Client p : x) {
                    arrayList.add(p.getId() + ": " + p.getFullName());
                }
                result.postValue(arrayList);
            });
            return result;
        } else {
            clientDAO.getAllEntities().observe(owner, x -> {
                for (Client p : x) {
                    arrayList.add(p.getId() + ": " + p.getFullName());
                }
                result.postValue(arrayList);
            });
            return result;
        }
    }

    @Override
    public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
        MutableLiveData<SavingObject> result = new MutableLiveData<>();
        clientDAO.getEntityById(id).observe(owner, x -> {
            result.postValue(x);
        });
        return result;
    }

    //public LiveData<List<Client>> getAllEntities(){
//        return clientDAO.getAllEntities();
//    }
}
