package com.example.nic_validation.controller;

import com.example.nic_validation.entity.PersonEntity;
import com.example.nic_validation.model.Person;
import com.example.nic_validation.model.Respond;
import com.example.nic_validation.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Respond<List<PersonEntity>> getAllPerson() {
        return personService.getAll();
    }

    @PostMapping
    public Respond<ArrayList<Person>> nicValidation(@RequestBody List<Person> persons) {
        return personService.nicValidation(persons);

    }

    @PostMapping("save")
    public Respond<ArrayList<PersonEntity>> save(@RequestBody List<Person> persons) {
        return  personService.save(persons);
    }
}
