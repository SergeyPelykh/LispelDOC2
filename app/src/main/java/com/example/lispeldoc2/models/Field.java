package com.example.lispeldoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "field_table")
public class Field {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int inputType;
    private int nameTextViewVisibility;
    private int nameTextEditVisibility;
    private int imageButtonVisibility;
    private int inscriptionTextViewVisibility;
    private int listDataVisibility;
    private String dataSource;
    private String hint;
    private String inscription;
    private String savedValueName;
    private String linkedValueName;

    public Field() {
        this.nameTextViewVisibility = 0x00000000;
        this.nameTextEditVisibility = 0x00000004;
        this.imageButtonVisibility = 0x00000004;
        this.inscriptionTextViewVisibility = 0x00000000;
        this.listDataVisibility = 0x00000008;
    }

    public String getSavedValueName() {
        return savedValueName;
    }

    public void setSavedValueName(String savedValueName) {
        this.savedValueName = savedValueName;
    }

    public String getLinkedValueName() {
        return linkedValueName;
    }

    public void setLinkedValueName(String linkedValueName) {
        this.linkedValueName = linkedValueName;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getInscription() {
        return inscription;
    }

    public void setInscription(String inscription) {
        this.inscription = inscription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNameTextViewVisibility() {
        return nameTextViewVisibility;
    }

    public void setNameTextViewVisibility(int nameTextViewVisibility) {
        this.nameTextViewVisibility = nameTextViewVisibility;
    }

    public int getNameTextEditVisibility() {
        return nameTextEditVisibility;
    }

    public void setNameTextEditVisibility(int nameTextEditVisibility) {
        this.nameTextEditVisibility = nameTextEditVisibility;
    }

    public int getImageButtonVisibility() {
        return imageButtonVisibility;
    }

    public void setImageButtonVisibility(int imageButtonVisibility) {
        this.imageButtonVisibility = imageButtonVisibility;
    }

    public int getInscriptionTextViewVisibility() {
        return inscriptionTextViewVisibility;
    }

    public void setInscriptionTextViewVisibility(int inscriptionTextViewVisibility) {
        this.inscriptionTextViewVisibility = inscriptionTextViewVisibility;
    }

    public int getListDataVisibility() {
        return listDataVisibility;
    }

    public void setListDataVisibility(int listDataVisibility) {
        this.listDataVisibility = listDataVisibility;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
