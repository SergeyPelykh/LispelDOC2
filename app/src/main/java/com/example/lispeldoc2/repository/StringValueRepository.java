package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.StringValueDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.models.StringValue;

import java.util.ArrayList;
import java.util.List;

public class StringValueRepository implements Repository {
    private String title;
    private StringValueDAO stringValueDAO;

    public StringValueRepository(Application application, String title) {
        LispelRoomDatabase database =LispelRoomDatabase.getDatabase(application);
        this.stringValueDAO = database.stringValueDAO();
        this.title = title;
    }

    public LiveData<Long> insert(StringValue stringValue){
        MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutableLiveData.postValue(stringValueDAO.insert(stringValue));
            }
        }).start();
        return mutableLiveData;
    }

    public LiveData<List<StringValue>> getAllEntities(){
        return stringValueDAO.getAllEntities(title);
    }

    @Override
    public LiveData<Long> insert(SavingObject savingObject) {
        return null;
    }

    @Override
    public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
        return null;
    }

    @Override
    public LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property) {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        ArrayList<String> arrayList = new ArrayList();
        if (!property.equals("")){
            stringValueDAO.getAllEntitiesByName(title, "%" + property + "%").observe(owner, x -> {
                for (StringValue p : x) {
                    arrayList.add(p.getId() + ": " + p.getName());
                }
                result.postValue(arrayList);
            });
            return result;
        } else {
            stringValueDAO.getAllEntitiesByName(title,"%" + property + "%").observe(owner, x -> {
                for (StringValue p : x) {
                    arrayList.add(p.getId() + ": " + p.getName());
                }
                result.postValue(arrayList);
            });
            return result;
        }
    }

    @Override
    public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
        return null;
    }
}
