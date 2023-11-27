package com.example.lispelDoc2.interfaces;

public interface SavingObject {
    Long getId();
    String getDescription();
    default void setTitle(String title){};
}
