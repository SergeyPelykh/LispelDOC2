package com.example.lispeldoc2.models;

import com.example.lispeldoc2.interfaces.LispelAddValueByUser;
import com.example.lispeldoc2.interfaces.LispelCreateRepository;
import com.example.lispeldoc2.interfaces.RepositoryEnum;

import java.util.ArrayList;

public class Order {
    @LispelAddValueByUser(number = 1,
            name = "toner",
            name_hint = "toner",
            name_title = "toner",
            input_type = 8192,
            base = RepositoryEnum.SAVE_IN_BASE,
            class_entity = "com.example.lispeldoc2.models.Toner"
    )
//            name = "клиент",
//            name_hint = "клиент",
//            name_title = "клиент",
//            input_type = 8192,
//            base = RepositoryEnum.SAVE_IN_BASE,
//            repository = com.example.lispeldoc2.repository.ClientRepository.class,
//            class_entity = "com.example.lispeldoc2.models.Client")
   // @LispelCreateRepository(dao = com.example.lispeldoc2.dao.TonerDAO.class)
    private Client client;
    @LispelAddValueByUser(number = 2,
            name = "услуги",
            name_hint = "услуги",
            name_title = "услуги",
            input_type = 8192,
            base = RepositoryEnum.READ_FROM_BASE_AND_EDIT,
            repository = com.example.lispeldoc2.repository.PrintUnitRepository.class)
    private ArrayList<Service> services;
    @LispelAddValueByUser(number = 3,
            name = "итоговая цена",
            name_hint = "итоговая цена",
            name_title = "итоговая цена",
            input_type = 3)
    private Long finalPrice;
}
