package com.example.student.studentdatabasewithfirebase;

public class Student {

    String id,name, dept, user_id;

    public Student() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getUser_id() {
        return user_id;
    }

    public Student(String id, String name, String dept, String user_id) {
        this.id = id;
        this.name = name;
        this.dept = dept;

        this.user_id = user_id;
    }
}
