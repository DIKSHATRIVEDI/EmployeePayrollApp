package com.example.employeepayrollapp.service;

import com.example.employeepayrollapp.Interface.IEmployeeService;
import com.example.employeepayrollapp.dto.EmployeeDTO;
import com.example.employeepayrollapp.model.Employee;
import com.example.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return an empty list in case of failure
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            Optional<Employee> employee = repository.findById(id);
            if (employee.isPresent()) {
                return employee.get();
            } else {
                System.out.println("Employee with ID " + id + " not found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setSalary(employeeDTO.getSalary());
            return repository.save(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        try {
            Optional<Employee> employeeOpt = repository.findById(id);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                employee.setName(employeeDTO.getName());
                employee.setSalary(employeeDTO.getSalary());
                return repository.save(employee);
            } else {
                System.out.println("Employee with ID " + id + " not found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteEmployee(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                System.out.println("Employee with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
