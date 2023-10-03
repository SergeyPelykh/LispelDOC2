package com.example.lispeldoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.lispeldoc2.interfaces.AddValueByUser;
import com.example.lispeldoc2.interfaces.SavingObject;
import com.example.lispeldoc2.utilities.Convert;

import java.util.Date;

@Entity(tableName = "printUnit_table")
public class PrintUnit implements SavingObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @TypeConverters({Convert.class})
    private Date dateOfCreate;
    @AddValueByUser
    private String partName;
    @AddValueByUser
    private String vendor;
    @AddValueByUser
    private String model;
    @AddValueByUser
    private String originality;
    @AddValueByUser
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
