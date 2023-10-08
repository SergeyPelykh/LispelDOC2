package com.example.lispeldoc2.models;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;

import java.util.ArrayList;

public class Order {
    @LispelAddValueByUser(number = 1,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            input_type = 8192)
    private Client client;
    @LispelAddValueByUser(number = 2,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            input_type = 8192)
    private ArrayList<Service> services;
    @LispelAddValueByUser(number = 3,
            name = "номер стикера",
            name_hint = "номер стикера",
            name_title = "номер стикера",
            input_type = 8192)
    private Long finalPrice;
}
