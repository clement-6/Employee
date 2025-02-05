package com.em.employeemanagement.Repository;


import com.em.employeemanagement.Entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    void shouldGetAllEmployee(){
        List<Employee> employeeList = employeeRepo.findAll();
        assertEquals(3, employeeList.size());

    }

}