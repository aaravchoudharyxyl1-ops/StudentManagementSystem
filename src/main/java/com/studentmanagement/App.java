package com.studentmanagement;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        try {

            StudentManager manager = new StudentManager();

            // Check database connection before starting
            try (var connection = Database.connect()) {

                System.out.println("Connected to MySQL successfully.");

            } catch (SQLException e) {

                System.err.println(
                        "Cannot connect to MySQL: " + e.getMessage()
                );

                System.err.println(
                        "See README.md for setup instructions."
                );

                return;
            }

            // Start Student Management System
            try (Scanner scanner = new Scanner(System.in)) {

                while (true) {

                    System.out.println(
                            "\n===== STUDENT MANAGEMENT SYSTEM ====="
                    );

                    System.out.println("1. Add Student");
                    System.out.println("2. View All Students");
                    System.out.println("3. Search Student");
                    System.out.println("4. Update Student");
                    System.out.println("5. Delete Student");
                    System.out.println("0. Exit");

                    int choice = readInt(
                            scanner,
                            "Choose an option: "
                    );

                    try {

                        switch (choice) {

                            // ADD STUDENT
                            case 1 -> {

                                int id = readInt(
                                        scanner,
                                        "Student ID: "
                                );

                                String name = readName(
                                        scanner,
                                        "Student name: "
                                );

                                int age = readInt(
                                        scanner,
                                        "Age: "
                                );

                                manager.addStudent(
                                        new Student(id, name, age)
                                );

                                System.out.println(
                                        "Student added successfully."
                                );
                            }

                            // VIEW ALL STUDENTS
                            case 2 -> {

                                List<Student> students =
                                        manager.getAllStudents();

                                if (students.isEmpty()) {

                                    System.out.println(
                                            "No students found."
                                    );

                                } else {

                                    students.forEach(
                                            Student::displayStudent
                                    );
                                }
                            }

                            // SEARCH STUDENT
                            case 3 -> {

                                int id = readInt(
                                        scanner,
                                        "Student ID to search: "
                                );

                                manager.findStudent(id)
                                        .ifPresentOrElse(

                                                Student::displayStudent,

                                                () -> System.out.println(
                                                        "Student not found."
                                                )
                                        );
                            }

                            // UPDATE STUDENT
                            case 4 -> {

                                int id = readInt(
                                        scanner,
                                        "Student ID to update: "
                                );

                                if (manager.findStudent(id).isEmpty()) {

                                    System.out.println(
                                            "Student not found."
                                    );

                                } else {

                                    String name = readName(
                                            scanner,
                                            "New name: "
                                    );

                                    int age = readInt(
                                            scanner,
                                            "New age: "
                                    );

                                    manager.updateStudent(
                                            id,
                                            name,
                                            age
                                    );

                                    System.out.println(
                                            "Student updated successfully."
                                    );
                                }
                            }

                            // DELETE STUDENT
                            case 5 -> {

                                int id = readInt(
                                        scanner,
                                        "Student ID to delete: "
                                );

                                boolean deleted =
                                        manager.deleteStudent(id);

                                if (deleted) {

                                    System.out.println(
                                            "Student deleted."
                                    );

                                } else {

                                    System.out.println(
                                            "Student not found."
                                    );
                                }
                            }

                            // EXIT APPLICATION
                            case 0 -> {

                                System.out.println("Goodbye!");

                                return;
                            }

                            // INVALID MENU OPTION
                            default -> System.out.println(
                                    "Invalid option. Try again."
                            );
                        }

                    } catch (SQLException e) {

                        System.out.println(
                                "Database error: " + e.getMessage()
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println(
                                "Error: " + e.getMessage()
                        );
                    }
                }
            }

        } finally {

            // Shut down MySQL Connector/J background cleanup thread
            AbandonedConnectionCleanupThread.checkedShutdown();

        }
    }

    // READ INTEGER INPUT
    private static int readInt(
            Scanner scanner,
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid whole number."
                );
            }
        }
    }

    // READ STUDENT NAME
    private static String readName(
            Scanner scanner,
            String prompt
    ) {

        System.out.print(prompt);

        return scanner.nextLine();
    }
}