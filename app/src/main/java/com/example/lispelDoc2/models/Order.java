package com.example.lispelDoc2.models;

import com.example.lispelDoc2.interfaces.LispelAddValueByUser;
import com.example.lispelDoc2.interfaces.RepositoryEnum;

import java.util.ArrayList;

public class Order {
    @LispelAddValueByUser(number = 1,
            name = "клиент",
            name_hint = "клиент",
            name_title = "клиент",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.Service"
    )
    private Client client;
    @LispelAddValueByUser(number = 2,
            name = "услуги",
            name_hint = "услуги",
            name_title = "услуги",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.Component")
    private ArrayList<Service> services;
    @LispelAddValueByUser(number = 3,
            name = "итоговая цена",
            name_hint = "итоговая цена",
            name_title = "итоговая цена",
            input_type = 1,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispelDoc2.models.OrderUnit")
    private Long finalPrice;
}
