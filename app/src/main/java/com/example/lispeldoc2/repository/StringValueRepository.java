package com.example.lispeldoc2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lispeldoc2.dao.StringValueDAO;
import com.example.lispeldoc2.database.LispelRoomDatabase;
import com.example.lispeldoc2.models.StringValue;

import java.util.List;

public class StringValueRepository {
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

}
