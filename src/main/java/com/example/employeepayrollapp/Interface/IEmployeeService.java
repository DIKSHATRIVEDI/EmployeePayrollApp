package com.example.employeepayrollapp.Interface;

import com.example.employeepayrollapp.dto.EmployeeDTO;
import com.example.employeepayrollapp.model.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees(String token);
    Employee getEmployeeById(Long id, String token);
    Employee createEmployee(EmployeeDTO employeeDTO, String token);
    Employee updateEmployee(Long id, EmployeeDTO employeeDTO, String token);
    void deleteEmployee(Long id, String token);
}
