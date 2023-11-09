package com.example.lispeldoc2.models;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateRepository;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.repository.ClientRepository;

import java.util.ArrayList;

@LispelCreateRepository(
        dao = com.example.lispeldoc2.repository.TonerFlatRepository.class,
        entity = com.example.lispeldoc2.models.Toner.class)
@Entity(tableName = "toner_table")
public class Toner implements SavingObject {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @LispelAddValueByUser(number = 1,
            name = "название",
            name_hint = "название",
            name_title = "название",
            input_type = 8192)
    private String name;
    @LispelAddValueByUser(number = 2,
            name = "полное название",
            name_hint = "полное название",
            name_title = "полное название",
            input_type = 8192)
    private String fullName;
    @LispelAddValueByUser(number = 3,
            name = "производитель",
            name_hint = "производитель",
            name_title = "производитель",
            input_type = 8192)
    private String vendor;
    @LispelAddValueByUser(number = 4,
            name = "поставщик",
            name_hint = "поставщик",
            name_title = "поставщик",
            input_type = 8192)
    private String provider;
//    private ArrayList<String> compatibilityList;
//    private long weight;


    public Toner() {
    }

    public Toner(ArrayList<String>arr) {
        this.name = arr.get(0);
        this.fullName = arr.get(1);
        this.vendor = arr.get(2);
        this.provider = arr.get(3);
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Repository getRepository(Application application, String title) {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getDescription() {
        return this.name + " " + this.fullName;
    }

    @Override
    public SavingObject createEntityFromList(ArrayList<String> arrayList) {
        return null;
    }
}
