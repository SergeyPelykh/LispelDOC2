package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.TonerDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.interfaces.LispelDAO;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.Toner;

import java.util.ArrayList;
import java.util.List;

public class TonerFlatRepository extends LispelDAO {
    private TonerDAO tonerDAO;

    public TonerFlatRepository(Application application) {
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.tonerDAO = lispelRoomDatabase.tonerDAO();
    }

    @Override
    public Long insert(SavingObject savingObject) {
        return tonerDAO.insert((Toner) savingObject);
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner) {
        MutableLiveData<List<SavingObject>> result = new MutableLiveData<>();
        ArrayList<SavingObject> arr = new ArrayList<>();
        tonerDAO.getAllEntities().observe(owner, x -> {
            if (!x.isEmpty()){
                for (Toner t : x) {
                    arr.add(t);
                }
            }
            result.postValue(arr);
        });
        return result;
    }

    @Override
    public LiveData<List<SavingObject>> getAllEntitiesByModel(LifecycleOwner owner, String s) {
        MutableLiveData<List<SavingObject>> result = new MutableLiveData<>();
        ArrayList<SavingObject> arr = new ArrayList<>();
        tonerDAO.getAllEntitiesByName(s).observe(owner, x -> {
            if (!x.isEmpty()){
                for (Toner t : x) {
                    arr.add(t);
                }
            }
            result.postValue(arr);
        });
        return result;
    }

    @Override
    public LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id) {
        MutableLiveData<SavingObject> result = new MutableLiveData<>();
        tonerDAO.getEntityById(id).observe(owner, x -> {
            result.postValue(x);
        });
        return result;
    }
}
