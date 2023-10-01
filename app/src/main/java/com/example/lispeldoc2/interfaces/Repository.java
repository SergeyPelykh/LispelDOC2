package com.example.lispeldoc2.interfaces;

import androidx.lifecycle.LiveData;

import com.example.lispeldoc2.interfaces.SavingObject;

import java.util.ArrayList;
import java.util.List;

public interface Repository {
    LiveData<Long> insert(SavingObject savingObject);
    LiveData<List<SavingObject>> getAllEntities();
    LiveData<? extends SavingObject> getEntityByProperty(String property);
}
