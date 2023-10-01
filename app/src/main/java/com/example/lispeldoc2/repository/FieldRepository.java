package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.FieldDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.models.Field;
import com.example.lispeldoc2.models.PrintUnit;

import java.util.Date;

public class FieldRepository {
    private FieldDAO fieldDAO;

    public FieldRepository(Application application){
        LispelRoomDatabase lispelRoomDatabase = LispelRoomDatabase.getDatabase(application);
        this.fieldDAO = lispelRoomDatabase.fieldDAO();
    }

    public LiveData<Long> insert(Field field){
        MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutableLiveData.postValue(fieldDAO.insert(field));
            }
        }).start();
        return mutableLiveData;
    }
    //public LiveData<Field> getEntity
}
