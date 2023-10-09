package com.example.lispeldoc2.models;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;

import java.util.ArrayList;

public class Order {
    @LispelAddValueByUser(number = 1,
            name = "клиент",
            name_hint = "клиент",
            name_title = "клиент",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private Client client;
    @LispelAddValueByUser(number = 2,
            name = "услуги",
            name_hint = "услуги",
            name_title = "услуги",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private ArrayList<Service> services;
    @LispelAddValueByUser(number = 3,
            name = "итоговая цена",
            name_hint = "итоговая цена",
            name_title = "итоговая цена",
            input_type = 8192,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private Long finalPrice;
}
