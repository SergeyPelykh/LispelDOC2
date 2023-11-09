package com.example.lispeldoc2.models;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateFieldObject;
import com.example.lispeldoc2.interfaces.Repository;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.repository.PrintUnitRepository;
import com.example.lispeldoc2.utilities.Convert;

import java.util.ArrayList;
import java.util.Date;
@LispelCreateFieldObject(repository_class = PrintUnitRepository.class)
@Entity(tableName = "printUnit_table")
public class PrintUnit implements SavingObject{


    @Override
    public Repository getRepository(Application application, String title) {
        return new PrintUnitRepository(application);
    }

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @TypeConverters({Convert.class})
    private Date dateOfCreate;
    @LispelAddValueByUser(number = 2,
            name = "тип",
            name_hint = "тип",
            name_title = "тип",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private String partName;
    @LispelAddValueByUser(number = 3,
            name = "производитель",
            name_hint = "производитель",
            name_title = "производитель",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private String vendor;
    @LispelAddValueByUser(number = 4,
            name = "модель",
            name_hint = "модель",
            name_title = "модель",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private String model;
    @LispelAddValueByUser(number = 5,
            name = "совместимость",
            name_hint = "совместимость",
            name_title = "совместимость",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private String originality;
    @LispelAddValueByUser(number = 1,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private String number;
    private String oldNumbers;

    public void changeNumber(String newNumber){
        this.oldNumbers = this.oldNumbers + "|" + this.number;
        this.number = newNumber;
    }

    public PrintUnit() {
        this.dateOfCreate = new Date();
        this.number = "LS24";
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return vendor + " " + model;
    }

    @Override
    public SavingObject createEntityFromList(ArrayList<String> arrayList) {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOriginality() {
        return originality;
    }

    public void setOriginality(String originality) {
        this.originality = originality;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOldNumbers() {
        return oldNumbers;
    }

    public void setOldNumbers(String oldNumbers) {
        this.oldNumbers = oldNumbers;
    }
}
