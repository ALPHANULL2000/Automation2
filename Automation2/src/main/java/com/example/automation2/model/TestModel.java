package com.example.automation2.model;

import org.springframework.stereotype.Component;

import java.util.Objects;



@Component
public class TestModel {

    private Integer arg1;
    private String  arg2;

    public Integer getArg1() { return arg1; }
    public void setArg1(Integer arg1) { this.arg1 = arg1; }

    public String getArg2() { return arg2; }
    public void setArg2(String arg2) { this.arg2 = arg2; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestModel testModel = (TestModel) o;
        return Objects.equals(arg1, testModel.arg1) && Objects.equals(arg2, testModel.arg2);
    }

    @Override public int hashCode() { return Objects.hash(arg1, arg2); }

    @Override
    public String toString() {
        return "TestModel{" +
                "arg1=" + arg1 +
                ", arg2='" + arg2 + '\'' +
                '}';
    }
}


