package com.juandavyc.SpringSecEx.model;

public class Student {

    private Integer id;
    private String name;
    private Integer average;

    public Student(Integer id, String name, Integer average) {
        this.id = id;
        this.name = name;
        this.average = average;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", average=" + average +
                '}';
    }
}
