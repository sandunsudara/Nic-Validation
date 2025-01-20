package com.example.nic_validation.service.impl;

import com.example.nic_validation.entity.PersonEntity;
import com.example.nic_validation.model.Gender;
import com.example.nic_validation.model.Person;
import com.example.nic_validation.model.Respond;
import com.example.nic_validation.repository.PersonRepo;
import com.example.nic_validation.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Executor taskExecutor;

    /**
     * Validates a list of NICs for a group of persons concurrently.
     */
    @Override
    public Respond<ArrayList<Person>> nicValidation(List<Person> persons) {
        ArrayList<Person> validatedPersons = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(persons.size());
        try {
            for (Person person : persons) {
                taskExecutor.execute(() -> {
                    try {
                        validatedPersons.add(nicValidationLogic(person));
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await(); // Wait for all tasks to complete
            return new Respond<>(true, "Validation successful", validatedPersons);
        } catch (Exception e) {
            return new Respond<>(false, e.getMessage(), null);
        }
    }

    /**
     * Saves a list of persons to the database concurrently.
     */
    @Override
    public Respond<ArrayList<PersonEntity>> save(List<Person> persons) {
        ArrayList<PersonEntity> savedData = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(persons.size());
        try {
            for (Person person : persons) {
                taskExecutor.execute(() -> {
                    try {
                        PersonEntity personEntity = objectMapper.convertValue(person, PersonEntity.class);
                        savedData.add(personRepo.save(personEntity));
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await(); // Wait for all tasks to complete
            return new Respond<>(true, "All records saved", savedData);
        } catch (Exception e) {
            return new Respond<>(false, e.getMessage(), null);
        }
    }

    /**
     * Retrieves all record from person table
     */
    @Override
    public Respond<List<PersonEntity>> getAll() {
        try {
            List<PersonEntity> all = personRepo.findAll();
            return new Respond<>(true, "All recoard retrived", all);

        } catch (Exception e) {
            return new Respond<>(false, e.getMessage(), null);
        }
    }

    /**
     * Contains the logic for validating a person's NIC and extracting details such as gender and date of birth.
     */
    public Person nicValidationLogic(Person person) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String nic = person.getNic();

        if (nic.length() != 10 && nic.length() != 12) {
            person.setValidNic(false);
            return person;
        }

        if (nic.length() == 10) {
            if (nic.charAt(person.getNic().length() - 1) == 'v' || nic.charAt(person.getNic().length() - 1) == 'V')
                nic = nic.substring(0, nic.length() - 1);
            else {
                person.setValidNic(false);
                return person;
            }
        }

        person.setValidNic(true);
        if (nic.length() == 9) {
            try {
                int year = Integer.parseInt(nic.substring(0, 2)) + 1900;
                int gen = Integer.parseInt(nic.substring(2, 5));
                if (gen > 500) {
                    person.setGender(Gender.FEMALE);
                    int day = gen - 500;

                } else {
                    person.setGender(Gender.MALE);
                    LocalDate localDate = LocalDate.ofYearDay(year, gen);
                    person.setDOB(localDate);
                }

            } catch (DateTimeException e) {
                person.setValidNic(false);
                return person;
            }


            return person;
        } else {
            try {
                int year = Integer.parseInt(nic.substring(0, 4));
                int gen = Integer.parseInt(nic.substring(4, 7));
                if (gen > 500) {
                    person.setGender(Gender.FEMALE);
                    int day = gen - 500;
                    LocalDate localDate = LocalDate.ofYearDay(year, day);
                    person.setDOB(localDate);
                } else {
                    person.setGender(Gender.MALE);
                    LocalDate localDate = LocalDate.ofYearDay(year, gen);
                    person.setDOB(localDate);
                }
            } catch (DateTimeException e) {
                person.setValidNic(false);
                return person;
            }
            return person;
        }
    }


}
