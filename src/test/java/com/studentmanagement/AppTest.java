package com.studentmanagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void studentModelValidation() {
        Student student = new Student(101, "Rahul", 20);
        student.setName("Rahul Sharma");
        student.setAge(21);
        assertEquals(101, student.getId());
        assertEquals("Rahul Sharma", student.getName());
        assertEquals(21, student.getAge());
        assertThrows(IllegalArgumentException.class, () -> new Student(0, "A", 20));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, " ", 20));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, "A", -1));
    }
}
