package com.example.rqchallenge.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EmployeeListResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private List<Employee> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }
}
