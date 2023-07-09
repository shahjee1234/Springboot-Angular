package com.example.springbootbackend.repository;


import com.example.springbootbackend.model.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
//    Page<Employee> findAll(int offset, int pageSize);
  //  public Page<Employee> getAllEmployees(@Param("userId")int userId,Pageable perpageable);

    public List<Employee>findByFirstNameContaining(String name);

}
