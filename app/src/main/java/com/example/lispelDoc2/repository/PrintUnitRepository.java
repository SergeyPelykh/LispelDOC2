package com.example.lispelDoc2.repository;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispelDoc2.dao.PrintUnitDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.interfaces.Repository;
import com.example.lispelDoc2.interfaces.SavingObject;
import com.example.lispelDoc2.models.PrintUnit;

import java.util.ArrayList;
import java.util.List;

public class PrintUnitRepository implements Repository {
    private PrintUnitDAO printUnitDAO;

    public PrintUnitRepository(Application application){
        LispelRoomDatabase lispelRoomDatabase =LispelRoomDatabase.getDatabase(application);
        this.printUnitDAO = lispelRoomDatabase.printUnitDAO();
    }
    @Override
    public LiveData<Long> insert(SavingObject savingObject){
        MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutableLiveData.postValue(printUnitDAO.insert((PrintUnit) savingObject));
            }
        }).start();
        return mutableLiveData;
    }

    @Override
    public LiveData<List<String>> getNameAllEntities(LifecycleOwner owner) {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        ArrayList<String> arrayList = new ArrayList();
        printUnitDAO.getAllEntities().observe(owner, x -> {
            for (PrintUnit p : x) {
                arrayList.add(p.getId() + ": " + p.getVendor() + " " + p.getModel());
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
            printUnitDAO.getAllEntitiesByModel("%" + property + "%").observe(owner, x -> {
                for (PrintUnit p : x) {
                    arrayList.add(p.getId() + ": " + p.getVendor() + " " + p.getModel());
                }
                result.postValue(arrayList);
            });
            return result;
        } else {
            printUnitDAO.getAllEntities().observe(owner, x -> {
                for (PrintUnit p : x) {
                    arrayList.add(p.getId() + ": " + p.getVendor() + " " + p.getModel());
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

    public LiveData<List<PrintUnit>> getAllEntities(){
        return printUnitDAO.getAllEntities();
    }



    public LiveData<? extends SavingObject> getEntityByProperty(String property) {
        return printUnitDAO.getEntityByModel("%" + property + "%");
    }



}
