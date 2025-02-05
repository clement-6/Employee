package com.em.employeemanagement.Services.service;

import com.em.employeemanagement.DTO.EmployeeDTO;
import com.em.employeemanagement.Entity.Employee;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface EmployeeService {

    Employee add(EmployeeDTO employeeDTO);
    Employee up(EmployeeDTO employeeDTO, long id);
    void delete(long id);
    List<Employee> listEmployee();
    Employee employeeById(long id);

    void generateExcel(HttpServletResponse response) throws IOException;
}
