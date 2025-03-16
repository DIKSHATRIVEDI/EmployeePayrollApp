package com.example.employeepayrollapp.controller;

import com.example.employeepayrollapp.dto.EmployeeDTO;
import com.example.employeepayrollapp.model.Employee;
import com.example.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeepayrollservice")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    // Get all employees
    @GetMapping("/all") // http://localhost:8080/employeepayrollservice/all?token=<token>
    public List<Employee> getAllEmployees(@RequestParam String token) {
        return service.getAllEmployees(token);
    }

    // Get employee by ID
    @GetMapping("/get/{id}") // http://localhost:8080/employeepayrollservice/get/1?token=<token>
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id, @RequestParam String token) {
        Employee employee = service.getEmployeeById(id, token);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    // Create employee
    @PostMapping("/create") // http://localhost:8080/employeepayrollservice/create?token=<token>
    public Employee createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, @RequestParam String token) {
        return service.createEmployee(employeeDTO, token);
    }

    // Update employee
    @PutMapping("/update/{id}") // http://localhost:8080/employeepayrollservice/update/1?token=<token>
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO, @RequestParam String token) {
        Employee updatedEmployee = service.updateEmployee(id, employeeDTO, token);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    // Delete employee
    @DeleteMapping("/delete/{id}") // http://localhost:8080/employeepayrollservice/delete/1?token=<token>
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @RequestParam String token) {
        service.deleteEmployee(id, token);
        return ResponseEntity.noContent().build();
    }
}
