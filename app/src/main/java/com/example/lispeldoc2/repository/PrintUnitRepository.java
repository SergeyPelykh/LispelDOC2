package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.PrintUnitDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.models.PrintUnit;

import java.util.Arrays;
import java.util.List;

public class PrintUnitRepository{
    private PrintUnitDAO printUnitDAO;

    public PrintUnitRepository(Application application){
        LispelRoomDatabase lispelRoomDatabase =LispelRoomDatabase.getDatabase(application);
        this.printUnitDAO = lispelRoomDatabase.printUnitDAO();
    }

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


    public LiveData<List<PrintUnit>> getAllEntities(){
        return printUnitDAO.getAllEntities();
    }


    public LiveData<PrintUnit> getEntityByProperty(String property) {
        return null;
    }
    public List<String> getListOfFields(){
        return Arrays.asList("number", "partName", "vendor", "model", "originality");
    }
}
