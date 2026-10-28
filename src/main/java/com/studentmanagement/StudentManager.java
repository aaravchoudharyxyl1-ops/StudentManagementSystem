package com.studentmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null.");
        if (findStudent(student.getId()).isPresent()) throw new IllegalArgumentException("Student ID already exists.");
        students.add(student);
    }

    public List<Student> getAllStudents() {
        return List.copyOf(students);
    }

    public Optional<Student> findStudent(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    public boolean updateStudent(int id, String name, int age) {
        Optional<Student> found = findStudent(id);
        if (found.isEmpty()) return false;
        if (name == null || name.isBlank() || age < 1 || age > 120)
            throw new IllegalArgumentException("Invalid name or age.");
        Student student = found.get();
        student.setName(name);
        student.setAge(age);
        return true;
    }

    public boolean deleteStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }
}
