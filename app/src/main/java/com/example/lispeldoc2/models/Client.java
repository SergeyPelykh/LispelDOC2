package com.example.lispeldoc2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.RepositoryEnum;
import com.example.lispeldoc2.repository.PrintUnitRepository;
import com.example.lispeldoc2.repository.StringValueRepository;

//@Entity(tableName = "client_table")
public class Client {
    //@PrimaryKey(autoGenerate = true)
    private long id;
    @LispelAddValueByUser(
            number = 1,
            name = "name",
            name_hint = "имя",
            name_title = "имя",
            base = RepositoryEnum.SAVE_IN_BASE,
            repository = StringValueRepository.class,
            class_entity = "com.example.lispeldoc2.models.Order",
            repository_title = "street",
            input_type = 8192)
    private String name;
    @LispelAddValueByUser(
            number = 2,
            name = "full name",
            name_hint = "полное имя",
            name_title = "полное имя",
            base = RepositoryEnum.READ_FROM_BASE,
            repository = PrintUnitRepository.class,
            input_type = 8192)
    private String fullName;
    @LispelAddValueByUser(number = 3,
            name = "address",
            name_title = "адрес",
            name_hint = "адрес",
            input_type = 8192)
    private String address;
    @LispelAddValueByUser(number = 4,
            name = "phone",
            name_hint = "телефон",
            name_title = "телефон",
            input_type = 3)
    private String phone;
    private String type;
}
