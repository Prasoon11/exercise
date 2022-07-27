package com.example.rqchallenge.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateEmployeeRsp {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private Employee data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
        this.data = data;
    }
}
