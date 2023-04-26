package model;

import java.io.Serializable;

public class Student implements Serializable {

    private final static long serialVersionUID = 1L;

    private String name;
    private int total;
    private int age = 20;
    private String gender;

    public Student(String name, int total) {
        this.name = name;
        this.total = total;
    }

    public Student(String name, int total, int age) {
        this.name = name;
        this.total = total;
        this.age = age;
    }

    public Student(String name, int total, int age, String gender) {
        this.name = name;
        this.total = total;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
