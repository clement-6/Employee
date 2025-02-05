package com.em.employeemanagement.Services.Implements;

import com.em.employeemanagement.DTO.EmployeeDTO;
import com.em.employeemanagement.Entity.Employee;
import com.em.employeemanagement.Exception.ApiRequestException;
import com.em.employeemanagement.Repository.EmployeeRepo;
import com.em.employeemanagement.Services.service.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Override
    public Employee add(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        getEmployee(employee,employeeDTO);
        employee.setCodeEmployee(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    @Override
    public Employee up(EmployeeDTO employeeDTO, long id) {
        Employee employee = findEmployee(id);
        getEmployee(employee,employeeDTO);
        employee.setCodeEmployee(employee.getCodeEmployee());
        return employeeRepo.save(employee);
    }

    private void getEmployee(Employee employee, EmployeeDTO employeeDTO){
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setJobTitle(employeeDTO.getJobTitle());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAddress(employeeDTO.getAddress());
        employee.setImageUrl(employeeDTO.getImageUrl());
    }

    private Employee findEmployee(long id){
       return employeeRepo.findById(id).orElseThrow(()-> new ApiRequestException("Employee with ID"+ id + "not found"));
    }

    @Override
    public void delete(long id) {
        if (!employeeRepo.existsById(id)){
            throw new ApiRequestException("Employee with ID"+ id + "not found");
        }
       employeeRepo.deleteById(id);
    }

    @Override
    public List<Employee> listEmployee() {return employeeRepo.findAll();}

    @Override
    public Employee employeeById(long id) {return findEmployee(id);}

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {

        List<Employee> employee = employeeRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employee Liste");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Code");
        row.createCell(1).setCellValue("Nom");
        row.createCell(2).setCellValue("Poste");
        row.createCell(3).setCellValue("Telephone");
        row.createCell(4).setCellValue("adresse");
        row.createCell(5).setCellValue("email");
        row.createCell(6).setCellValue("Url");


        int dataRowIndex = 1;

        for(Employee emp : employee){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(emp.getCodeEmployee());
            dataRow.createCell(1).setCellValue(emp.getName());
            dataRow.createCell(2).setCellValue(emp.getJobTitle());
            dataRow.createCell(3).setCellValue(emp.getPhone());
            dataRow.createCell(4).setCellValue(emp.getAddress());
            dataRow.createCell(5).setCellValue(emp.getEmail());
            dataRow.createCell(6).setCellValue(emp.getImageUrl());
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();



    }
}
