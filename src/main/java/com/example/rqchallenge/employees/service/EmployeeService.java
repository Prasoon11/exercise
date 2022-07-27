package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.dto.CreateEmployeeRsp;
import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.dto.EmployeeListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Component
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    public EmployeeService() {
        restTemplate = new RestTemplate();
    }

    public List<Employee> getAllEmployees() {
        EmployeeListResponse response = new EmployeeListResponse();
        try {
            URI uri = new URI(baseUrl + "/employees");
            response = restTemplate.getForObject(uri, EmployeeListResponse.class);
        } catch (URISyntaxException e) {
            logger.error("Endpoint URL is wrong", e);
        } catch (Exception e) {
            logger.error("Exception while getting details", e);
        }

        return response.getData();
    }

    public List<Employee> getEmployeeByNameSearch(String seachPattern) {
        List<Employee> allEmployees = getAllEmployees();
        List<Employee> response = new ArrayList<>();
        for (Employee employee : allEmployees) {
            if (employee.getEmployeeName().contains(seachPattern)) {
                response.add(employee);
            }
        }
        return response;
    }

    public Employee getEmployeeById(String id) {
        List<Employee> allEmployees = getAllEmployees();
        Employee response = null;
        if (allEmployees == null) return response;

        for (Employee employee : allEmployees) {
            if (employee.getId().equals(id)) {
                response = employee;
            }
        }
        return response;
    }

    public Integer getHighestSalaryOfEmployees() {
        Integer highestSal = Integer.MIN_VALUE;
        List<Employee> allEmployees = getAllEmployees();
        for (Employee emp : allEmployees) {
            highestSal = Math.max(highestSal, emp.getEmployeeSalary());
        }
        return highestSal;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getEmployeeSalary);
        PriorityQueue<Employee> pq = new PriorityQueue<>(comparator);
        List<Employee> employees = getAllEmployees();

        //use priority queue to keep track of employee with top 10 salary
        for (Employee emp : employees) {
            pq.offer(emp);
            if (pq.size() > 10) {
                Employee empPll = pq.poll();
            }
        }
        List<String> nameList = new ArrayList<>();
        while (!pq.isEmpty()) {
            Employee emp = pq.poll();
            nameList.add(emp.getEmployeeName());
        }
        return nameList;
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        Employee employee = new Employee();
        employee.setEmployeeAge(String.valueOf(employeeInput.get("age")));
        employee.setEmployeeSalary(Integer.parseInt((String) employeeInput.get("salary")));
        employee.setEmployeeName(String.valueOf(employeeInput.get("name")));

        CreateEmployeeRsp response = null;
        try {
            URI uri = new URI(baseUrl + "/create");
            response = restTemplate.postForObject(uri, employee, CreateEmployeeRsp.class);
        } catch (URISyntaxException e) {
            logger.error("Endpoint URL is wrong", e);
        } catch (Exception e) {
            logger.error("Exception while creating", e);
        }
        Employee res = new Employee();

        if (response != null && response.getData() != null) {
            res.setEmployeeName(response.getData().getEmployeeName());
            res.setEmployeeAge(response.getData().getEmployeeAge());
            res.setEmployeeSalary(response.getData().getEmployeeSalary());
            res.setId(response.getData().getId());
        }
        return res;
    }

    public String deleteEmployee(String id) {
        String response = "SUCCESS";
        try {
            Employee employee = getEmployeeById(id);
            if (employee == null) {
                response = "FAILURE";
            }
            URI uri = new URI(baseUrl + "/delete");
            restTemplate.delete(uri + "/" + id);
        } catch (URISyntaxException e) {
            logger.error("Endpoint URL is wrong", e);
            response = "FAILURE";
        } catch (Exception e) {
            logger.error("Exception while deleting", e);
            response = "FAILURE";
        }
        return response;
    }
}
