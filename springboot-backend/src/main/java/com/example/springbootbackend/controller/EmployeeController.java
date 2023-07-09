package com.example.springbootbackend.controller;
import com.example.springbootbackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")

public class EmployeeController {
    //to inject employeeRepository by spring container
    @Autowired
    private EmployeeRepository employeeRepository;
    @RequestMapping(value = "/listPageable", method = RequestMethod.GET)
    Page<Employee> employeesPageable(Pageable pageable) {
        return employeeRepository.findAll(pageable);

    }

    //search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
        System.out.println(query);
        List<Employee> employees =this.employeeRepository.findByFirstNameContaining(query);
        return ResponseEntity.ok(employees);

    }



    //get all employees rest api
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

//    public List<Employee> findProductWithSorting(String field){
//        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC,field));
//    }


//    public Page<Employee> findProductWithPagination(int offset, int pageSize){
//        Page<Employee> products = employeeRepository.findAll(PageRequest.of(offset, pageSize));
//        return products;
//    }

//    @GetMapping("/employees/{field}")
//
//    public List<Employee> getAllEmployeesWithSort(@PathVariable String field) {
//
//
//        return employeeRepository.findAll();
//    }


//    @GetMapping("/employees/pagination/{offset}/{pageSize}")
//
//    public List<Employee> getAllEmployeesWithPagination(@PathVariable int offset , @PathVariable int pageSize) {
//       Page<Employee> productsWithPagination= employeeRepository.findAll(offset,pageSize);


       // return employeeRepository.findAll();
//        return getAllEmployeesWithPagination(1,5);
//    }

    //create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    //get employee by ID rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}


