package com.example.lispeldoc2.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.PrintUnitDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.PrintUnit;
import com.example.lispeldoc2.utilities.Convert;

import java.util.ArrayList;
import java.util.Arrays;
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

    public LiveData<List<PrintUnit>> getAllEntities(){
        return printUnitDAO.getAllEntities();
    }



    public LiveData<? extends SavingObject> getEntityByProperty(String property) {
        return printUnitDAO.getEntityByModel("%" + property + "%");
    }



}
