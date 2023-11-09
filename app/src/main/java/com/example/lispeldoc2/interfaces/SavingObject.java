package com.example.lispeldoc2.interfaces;

import android.app.Application;

import java.util.ArrayList;

public interface SavingObject {
    Repository getRepository(Application application, String title);
    Long getId();
    String getDescription();
    SavingObject createEntityFromList(ArrayList<String> arrayList);
}
