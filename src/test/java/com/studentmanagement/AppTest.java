package com.studentmanagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void addFindUpdateDeleteStudent() {
        StudentManager manager = new StudentManager();
        manager.addStudent(new Student(101, "Rahul", 20));
        assertEquals(1, manager.getAllStudents().size());
        assertEquals("Rahul", manager.findStudent(101).orElseThrow().getName());
        assertTrue(manager.updateStudent(101, "Rahul Sharma", 21));
        assertEquals("Rahul Sharma", manager.findStudent(101).orElseThrow().getName());
        assertTrue(manager.deleteStudent(101));
        assertTrue(manager.findStudent(101).isEmpty());
    }

    @Test
    void rejectDuplicateIds() {
        StudentManager manager = new StudentManager();
        manager.addStudent(new Student(1, "Priya", 19));
        assertThrows(IllegalArgumentException.class,
                () -> manager.addStudent(new Student(1, "Amit", 20)));
    }

    @Test
    void rejectInvalidStudentData() {
        assertThrows(IllegalArgumentException.class, () -> new Student(0, "Test", 20));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, " ", 20));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, "Test", -1));
    }
}
