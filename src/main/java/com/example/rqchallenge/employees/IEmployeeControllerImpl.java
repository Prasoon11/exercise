package com.example.rqchallenge.employees;

import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class IEmployeeControllerImpl implements IEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
    }

    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployeeByNameSearch(searchString));
    }

    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(id);

        if(employee != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(employeeService.getEmployeeById(id));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getHighestSalaryOfEmployees());
    }

    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.createEmployee(employeeInput));
    }

    public ResponseEntity<String> deleteEmployeeById(String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.deleteEmployee(id));
    }
}
