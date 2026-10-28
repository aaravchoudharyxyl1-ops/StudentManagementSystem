package com.studentmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** MySQL-backed CRUD; each operation uses a short-lived JDBC connection. */
public class StudentManager {
    public void addStudent(Student student) throws SQLException {
        if (student == null) throw new IllegalArgumentException("Student cannot be null.");
        String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, student.getId()); ps.setString(2, student.getName()); ps.setInt(3, student.getAge());
            try { ps.executeUpdate(); }
            catch (SQLIntegrityConstraintViolationException e) { throw new IllegalArgumentException("Student ID already exists.", e); }
        }
    }
    public List<Student> getAllStudents() throws SQLException {
        List<Student> result = new ArrayList<>();
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement("SELECT id, name, age FROM students ORDER BY id"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
        }
        return result;
    }
    public Optional<Student> findStudent(int id) throws SQLException {
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement("SELECT id, name, age FROM students WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(new Student(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
                return Optional.empty();
            }
        }
    }
    public boolean updateStudent(int id, String name, int age) throws SQLException {
        // Validate before writing to the database.
        Student checked = new Student(id, name, age);
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement("UPDATE students SET name = ?, age = ? WHERE id = ?")) {
            ps.setString(1, checked.getName()); ps.setInt(2, checked.getAge()); ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }
    public boolean deleteStudent(int id) throws SQLException {
        try (Connection c = Database.connect(); PreparedStatement ps = c.prepareStatement("DELETE FROM students WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
