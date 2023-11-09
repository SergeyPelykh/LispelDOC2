package com.example.lispeldoc2.interfaces;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import java.util.List;

public interface Repository {
    LiveData<Long> insert(SavingObject savingObject);
    LiveData<List<String>> getNameAllEntities(LifecycleOwner owner);
    LiveData<List<String>> getNameAllEntitiesByProperty(LifecycleOwner owner, String property);
    LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id);
}
