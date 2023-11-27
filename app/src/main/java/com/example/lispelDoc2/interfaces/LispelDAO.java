package com.example.lispelDoc2.interfaces;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.List;

public interface LispelDAO {

    default void setTitle (String title) {};

    Long insert(SavingObject savingObject);

    LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner);

    LiveData<List<SavingObject>> getAllEntitiesByProperty(LifecycleOwner owner, String s);

    LiveData<? extends SavingObject> getEntityById(Long id);
}
