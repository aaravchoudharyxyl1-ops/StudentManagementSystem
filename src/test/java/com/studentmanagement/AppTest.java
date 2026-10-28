package com.studentmanagement;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void createValidStudent() {
        Student student = new Student(101, "Rahul", 20);
        assertEquals(101, student.getId());
        assertEquals("Rahul", student.getName());
        assertEquals(20, student.getAge());
    }

    @Test
    void rejectZeroId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(0, "Rahul", 20));
    }

    @Test
    void rejectNegativeId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(-5, "Rahul", 20));
    }

    @Test
    void acceptPositiveId() {
        Student student = new Student(1, "Rahul", 20);
        assertEquals(1, student.getId());
    }

    @Test
    void rejectNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, null, 20));
    }

    @Test
    void rejectEmptyName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "", 20));
    }

    @Test
    void rejectWhitespaceName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "   ", 20));
    }

    @Test
    void trimNameOnCreation() {
        Student student = new Student(1, "  Rahul  ", 20);
        assertEquals("Rahul", student.getName());
    }

    @Test
    void updateName() {
        Student student = new Student(1, "Rahul", 20);
        student.setName("Amit");
        assertEquals("Amit", student.getName());
    }

    @Test
    void trimUpdatedName() {
        Student student = new Student(1, "Rahul", 20);
        student.setName("  Amit Sharma  ");
        assertEquals("Amit Sharma", student.getName());
    }

    @Test
    void rejectNullNameUpdate() {
        Student student = new Student(1, "Rahul", 20);
        assertThrows(IllegalArgumentException.class,
                () -> student.setName(null));
    }

    @Test
    void rejectBlankNameUpdate() {
        Student student = new Student(1, "Rahul", 20);
        assertThrows(IllegalArgumentException.class,
                () -> student.setName("  "));
    }

    @Test
    void acceptMinimumAge() {
        Student student = new Student(1, "Rahul", 1);
        assertEquals(1, student.getAge());
    }

    @Test
    void acceptMaximumAge() {
        Student student = new Student(1, "Rahul", 120);
        assertEquals(120, student.getAge());
    }

    @Test
    void rejectZeroAge() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "Rahul", 0));
    }

    @Test
    void rejectNegativeAge() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "Rahul", -1));
    }

    @Test
    void rejectAgeAboveMaximum() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "Rahul", 121));
    }

    @Test
    void updateAge() {
        Student student = new Student(1, "Rahul", 20);
        student.setAge(25);
        assertEquals(25, student.getAge());
    }

    @Test
    void verifyToString() {
        Student student = new Student(101, "Rahul", 20);
        assertEquals(
                "Student{id=101, name='Rahul', age=20}",
                student.toString()
        );
    }

    @Test
    void verifyDisplayStudent() {
        Student student = new Student(101, "Rahul", 20);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(
                    output, true, StandardCharsets.UTF_8
            ));

            student.displayStudent();

        } finally {
            System.setOut(originalOutput);
        }

        String result = output.toString(StandardCharsets.UTF_8);

        assertTrue(result.contains("Student ID: 101"));
        assertTrue(result.contains("Name: Rahul"));
        assertTrue(result.contains("Age: 20"));
    }
}
