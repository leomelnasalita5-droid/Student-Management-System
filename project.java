
import java.util.Scanner;
import java.util.Arrays;

public class StudentManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static class Student {
        int id;
        String name;
        String course;
        double grade;

        Student(int id, String name, String course, double grade) {
            this.id = id;
            this.name = name;
            this.course = course;
            this.grade = grade;
        }
    }

    static class Node {
        Student student;
        Node next;

        Node(Student student) {
            this.student = student;
            this.next = null;
        }
    }

    static class StudentLinkedList {
        Node head;
        Node tail;
        int size = 0;

        void add(Student student) {
            Node newNode = new Node(student);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }

            size++;
            System.out.println("\nStudent added successfully!");
        }

        Node[] toArray() {
            Node[] students = new Node[size];
            Node current = head;
            int index = 0;

            while (current != null) {
                students[index++] = current;
                current = current.next;
            }

            return students;
        }

        Node binarySearch(int id) {
            Node[] students = toArray();

            Arrays.sort(students, (a, b) ->
                Integer.compare(a.student.id, b.student.id)
            );

            int left = 0;
            int right = students.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                int currentId = students[mid].student.id;

                if (currentId == id) {
                    return students[mid];
                } else if (currentId < id) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return null;
        }

        void display() {
            if (head == null) {
                System.out.println("\nNo students available.");
                return;
            }

            System.out.println("\n========== STUDENT LIST ==========");
            System.out.printf("%-10s %-20s %-15s %-10s%n",
                    "ID", "Name", "Course", "Grade");
            System.out.println("----------------------------------------------------------");

            Node current = head;

            while (current != null) {
                Student s = current.student;

                System.out.printf("%-10d %-20s %-15s %-10.2f%n",
                        s.id, s.name, s.course, s.grade);

                current = current.next;
            }
        }

        void searchStudent(int id) {
            Node found = binarySearch(id);

            if (found == null) {
                System.out.println("\nStudent not found.");
            } else {
                Student s = found.student;

                System.out.println("\nStudent Found!");
                System.out.println("ID: " + s.id);
                System.out.println("Name: " + s.name);
                System.out.println("Course: " + s.course);
                System.out.println("Grade: " + s.grade);
            }
        }

        void update(int id) {
            Node found = binarySearch(id);

            if (found == null) {
                System.out.println("\nStudent not found.");
                return;
            }

            System.out.println("\nCurrent Student Information:");
            System.out.println("Name: " + found.student.name);
            System.out.println("Course: " + found.student.course);
            System.out.println("Grade: " + found.student.grade);

            System.out.println("\nEnter New Information:");

            found.student.name = readString("Enter new name: ");
            found.student.course = readString("Enter new course: ");
            found.student.grade = readGrade("Enter new grade (0-100): ");

            System.out.println("\nStudent updated successfully!");
        }

        void delete(int id) {
            if (head == null) {
                System.out.println("\nNo students available.");
                return;
            }

            Node current = head;
            Node previous = null;

            while (current != null && current.student.id != id) {
                previous = current;
                current = current.next;
            }

            if (current == null) {
                System.out.println("\nStudent not found.");
                return;
            }

            if (previous == null) {
                head = current.next;
            } else {
                previous.next = current.next;
            }

            if (current == tail) {
                tail = previous;
            }

            size--;

            if (head == null) {
                tail = null;
            }

            System.out.println("\nStudent deleted successfully!");
        }

        void sortById() {
            sortStudents(1);
        }

        void sortByName() {
            sortStudents(2);
        }

        void sortByGrade() {
            sortStudents(3);
        }

        void sortStudents(int type) {
            if (size == 0) {
                System.out.println("\nNo students to sort.");
                return;
            }

            boolean swapped;
            Node current;

            do {
                swapped = false;
                current = head;

                while (current != null && current.next != null) {
                    Student first = current.student;
                    Student second = current.next.student;
                    boolean shouldSwap = false;

                    if (type == 1 && first.id > second.id) {
                        shouldSwap = true;
                    } else if (type == 2 &&
                            first.name.compareToIgnoreCase(second.name) > 0) {
                        shouldSwap = true;
                    } else if (type == 3 && first.grade < second.grade) {
                        shouldSwap = true;
                    }

                    if (shouldSwap) {
                        current.student = second;
                        current.next.student = first;
                        swapped = true;
                    }

                    current = current.next;
                }

            } while (swapped);

            if (type == 1) {
                System.out.println("\nStudents sorted by ID!");
            } else if (type == 2) {
                System.out.println("\nStudents sorted by Name!");
            } else {
                System.out.println("\nStudents sorted by Grade!");
            }

            display();
        }

        void studentRankings() {
            if (size == 0) {
                System.out.println("\nNo students available.");
                return;
            }

            Node[] students = toArray();

            Arrays.sort(students, (a, b) ->
                Double.compare(b.student.grade, a.student.grade)
            );

            System.out.println("\n========== STUDENT RANKINGS ==========");

            for (int i = 0; i < students.length; i++) {
                Student s = students[i].student;

                System.out.printf("%d. %-20s Grade: %.2f%n",
                        i + 1, s.name, s.grade);
            }
        }

        void classStatistics() {
            if (size == 0) {
                System.out.println("\nNo students available.");
                return;
            }

            double total = 0;
            double highest = Double.MIN_VALUE;
            double lowest = Double.MAX_VALUE;
            int passed = 0;
            int failed = 0;

            Node current = head;

            while (current != null) {
                double grade = current.student.grade;

                total += grade;

                if (grade > highest) {
                    highest = grade;
                }

                if (grade < lowest) {
                    lowest = grade;
                }

                if (grade >= 75) {
                    passed++;
                } else {
                    failed++;
                }

                current = current.next;
            }

            double average = total / size;

            System.out.println("\n========== CLASS STATISTICS ==========");
            System.out.println("Total Students: " + size);
            System.out.printf("Average Grade: %.2f%n", average);
            System.out.printf("Highest Grade: %.2f%n", highest);
            System.out.printf("Lowest Grade: %.2f%n", lowest);
            System.out.println("Passed: " + passed);
            System.out.println("Failed: " + failed);
        }

        void studentCount() {
            System.out.println("\nTotal Number of Students: " + size);
        }
    }

    static StudentLinkedList list = new StudentLinkedList();

    static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    static String readString(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    static double readGrade(String message) {
        while (true) {
            System.out.print(message);

            try {
                double grade = Double.parseDouble(sc.nextLine().trim());

                if (grade >= 0 && grade <= 100) {
                    return grade;
                }

                System.out.println("Grade must be between 0 and 100.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid grade. Please enter a number.");
            }
        }
    }

    static void addStudent() {
        int id = readInt("Enter Student ID: ");

        if (id <= 0) {
            System.out.println("\nStudent ID must be greater than zero.");
            return;
        }

        if (list.binarySearch(id) != null) {
            System.out.println("\nStudent ID already exists.");
            return;
        }

        String name = readString("Enter Student Name: ");
        String course = readString("Enter Course: ");
        double grade = readGrade("Enter Grade (0-100): ");

        Student student = new Student(id, name, course, grade);
        list.add(student);
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n          STUDENT MANAGEMENT SYSTEM");
            System.out.println("================================================");

            System.out.println("[1] Add Student");
            System.out.println("[2] View All Students");
            System.out.println("[3] Update Student");
            System.out.println("[4] Delete Student");
            System.out.println("[5] Search Student");
            System.out.println("[6] Sort by Student ID");
            System.out.println("[7] Sort by Student Name");
            System.out.println("[8] Sort by Grade");
            System.out.println("[9] Student Rankings");
            System.out.println("[10] Class Statistics");
            System.out.println("[11] View Student Count");
            System.out.println("[0] Exit");

            System.out.println("================================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    list.display();
                    break;

                case 3:
                    list.update(readInt("Enter Student ID to update: "));
                    break;

                case 4:
                    list.delete(readInt("Enter Student ID to delete: "));
                    break;

                case 5:
                    list.searchStudent(readInt("Enter Student ID to search: "));
                    break;

                case 6:
                    list.sortById();
                    break;

                case 7:
                    list.sortByName();
                    break;

                case 8:
                    list.sortByGrade();
                    break;

                case 9:
                    list.studentRankings();
                    break;

                case 10:
                    list.classStatistics();
                    break;

                case 11:
                    list.studentCount();
                    break;

                case 0:
                    System.out.println("\nThank you for using Student Management System!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
