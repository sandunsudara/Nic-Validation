package com.example.nic_validation.service;

import com.example.nic_validation.entity.PersonEntity;
import com.example.nic_validation.model.Person;
import com.example.nic_validation.model.Respond;

import java.util.ArrayList;
import java.util.List;

public interface PersonService {
    Respond<ArrayList<Person>> nicValidation(List<Person> persons);

    Respond<ArrayList<PersonEntity>> save(List<Person> persons);

    Respond<List<PersonEntity>> getAll();
}
