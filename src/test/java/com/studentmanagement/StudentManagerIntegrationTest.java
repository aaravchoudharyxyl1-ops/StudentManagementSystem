package com.studentmanagement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StudentManagerIntegrationTest {

    private final StudentManager manager = new StudentManager();

    @BeforeAll
    static void verifyTestDatabase() throws SQLException {
        String url = System.getenv("DB_URL");

        if (url == null ||
                !url.matches("(?i)^jdbc:mysql://[^/?]+/student_management_test(?:[?].*)?$")) {
            throw new IllegalStateException(
                "Integration tests require DB_URL pointing to student_management_test."
            );
        }

        try (Connection connection = Database.connect()) {
            if (!"student_management_test".equalsIgnoreCase(
                    connection.getCatalog())) {
                throw new IllegalStateException(
                    "Connected to the wrong database."
                );
            }
        }
    }

    private int uniqueId() {
        return 1_000_000 + Math.floorMod(
                UUID.randomUUID().hashCode(), 1_000_000_000);
    }

    private void cleanup(int id) throws SQLException {
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM students WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Test
    void addAndFindStudent() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Rahul", 20));

            Student found = manager.findStudent(id).orElseThrow();

            assertEquals(id, found.getId());
            assertEquals("Rahul", found.getName());
            assertEquals(20, found.getAge());
        } finally {
            cleanup(id);
        }
    }

    @Test
    void updateStudent() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Rahul", 20));

            assertTrue(manager.updateStudent(id, "Rahul Sharma", 21));

            Student updated = manager.findStudent(id).orElseThrow();

            assertEquals("Rahul Sharma", updated.getName());
            assertEquals(21, updated.getAge());
        } finally {
            cleanup(id);
        }
    }

    @Test
    void deleteStudent() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Rahul", 20));

            assertTrue(manager.deleteStudent(id));
            assertTrue(manager.findStudent(id).isEmpty());
        } finally {
            cleanup(id);
        }
    }

    @Test
    void getAllStudentsIncludesInsertedStudent() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Amit", 22));

            List<Student> students = manager.getAllStudents();

            assertTrue(students.stream().anyMatch(
                    student -> student.getId() == id
                            && student.getName().equals("Amit")
            ));
        } finally {
            cleanup(id);
        }
    }

    @Test
    void rejectDuplicateStudentId() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Rahul", 20));

            assertThrows(IllegalArgumentException.class,
                    () -> manager.addStudent(
                            new Student(id, "Amit", 22)));
        } finally {
            cleanup(id);
        }
    }

    @Test
    void rejectNullStudent() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.addStudent(null));
    }

    @Test
    void rejectInvalidUpdate() throws SQLException {
        int id = uniqueId();

        try {
            manager.addStudent(new Student(id, "Rahul", 20));

            assertThrows(IllegalArgumentException.class,
                    () -> manager.updateStudent(id, "", 21));

            assertEquals("Rahul",
                    manager.findStudent(id).orElseThrow().getName());
        } finally {
            cleanup(id);
        }
    }
}
