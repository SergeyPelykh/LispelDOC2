package com.example.lispeldoc2.interfaces;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lispeldoc2.models.PrintUnit;

import java.util.List;

public abstract class LispelDAO {

    public abstract Long insert(SavingObject savingObject);

    public abstract LiveData<List<SavingObject>> getAllEntities(LifecycleOwner owner);

    public abstract LiveData<List<SavingObject>> getAllEntitiesByModel(LifecycleOwner owner, String s);

    public abstract LiveData<SavingObject> getEntityById(LifecycleOwner owner, Long id);
}
