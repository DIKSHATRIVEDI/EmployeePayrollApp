package com.example.employeepayrollapp.service;

import com.example.employeepayrollapp.Interface.IEmployeeService;
import com.example.employeepayrollapp.dto.EmployeeDTO;
import com.example.employeepayrollapp.model.Employee;
import com.example.employeepayrollapp.repository.EmployeeRepository;
import com.example.employeepayrollapp.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    private JwtToken tokenUtil;

    // Method to check authentication
    private void checkAuthentication(Long userId, String token) {
        if (userId == null || !tokenUtil.isUserLoggedIn(userId, token)) {
            throw new RuntimeException("Unauthorized! Please log in first.");
        }
    }

    @Override
    @Cacheable(value = "employeePayrollCache") // Cache results
    public List<Employee> getAllEmployees(String token) {
        Long userId = tokenUtil.getCurrentUserId(token);
        checkAuthentication(userId, token);
        try {
            return repository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return an empty list in case of failure
        }
    }

    @Override
    @Cacheable(value = "employeePayrollCache", key = "#id") // Cache specific entry by ID
    public Employee getEmployeeById(Long id, String token) {
        Long userId = tokenUtil.getCurrentUserId(token);
        checkAuthentication(userId, token);
        try {
            Optional<Employee> employee = repository.findById(id);
            if (employee.isPresent()) {
                return employee.get();
            } else {
                throw new RuntimeException("Employee with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @CacheEvict(value = "employeePayrollCache", allEntries = true) // Clear cache on new entry
    public Employee createEmployee(EmployeeDTO employeeDTO, String token) {
        Long userId = tokenUtil.getCurrentUserId(token);
        checkAuthentication(userId, token);
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

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employeePayrollCache", allEntries = true),
            @CacheEvict(value = "employeePayrollCache", key = "#id")
    })
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO, String token) {
        Long userId = tokenUtil.getCurrentUserId(token);
        checkAuthentication(userId, token);
        try {
            Optional<Employee> employeeOpt = repository.findById(id);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                employee.setName(employeeDTO.getName());
                employee.setSalary(employeeDTO.getSalary());
                return repository.save(employee);
            } else {
                throw new RuntimeException("Employee with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @CacheEvict(value = "employeePayrollCache", key = "#id") // Remove from cache on delete
    public void deleteEmployee(Long id, String token) {
        Long userId = tokenUtil.getCurrentUserId(token);
        checkAuthentication(userId, token);
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new RuntimeException("Employee with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
