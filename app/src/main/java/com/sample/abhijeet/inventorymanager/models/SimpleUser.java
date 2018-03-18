package com.sample.abhijeet.inventorymanager.models;

/**
 * Created by abhi2 on 3/18/2018.
 */

public class SimpleUser
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    int id;
    String name;
    int age;
    int salary;
}
