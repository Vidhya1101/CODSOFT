import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolled;

    public Course(String courseCode, String title, String description, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public boolean isAvailable() {
        return enrolled < capacity;
    }

    public void enrollStudent() {
        if (isAvailable()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }
}

class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.isAvailable()) {
            registeredCourses.add(course);
            course.enrollStudent();
            System.out.println("You have successfully registered for: " + course.title);
        } else {
            System.out.println("Course " + course.title + " is full!");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            System.out.println("You have successfully dropped: " + course.title);
        } else {
            System.out.println("You are not registered for this course.");
        }
    }
}

public class StudentCourseRegistrationSystem {
    private static List<Course> courseCatalog = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        initializeCourses();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentID, name);
        studentList.add(student);

        boolean continueProgram = true;
        while (continueProgram) {
            System.out.println("\n1. View Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    System.out.print("Enter the course code to register: ");
                    String courseCode = scanner.nextLine();
                    Course courseToRegister = findCourseByCode(courseCode);
                    if (courseToRegister != null) {
                        student.registerCourse(courseToRegister);
                    } else {
                        System.out.println("Course not found!");
                    }
                    break;
                case 3:
                    System.out.print("Enter the course code to drop: ");
                    String courseCodeToDrop = scanner.nextLine();
                    Course courseToDrop = findCourseByCode(courseCodeToDrop);
                    if (courseToDrop != null) {
                        student.dropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found!");
                    }
                    break;
                case 4:
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private static void initializeCourses() {
        courseCatalog.add(new Course("CS101", "Introduction to Computer Science", "Learn the basics of computer science.", 3));
        courseCatalog.add(new Course("UHV45", "Human Values", "A Foundation Course in Human Values and Professional Ethics", 2));
        courseCatalog.add(new Course("ES205", "Environmental Science", "Examine the interaction between humans and the environment", 2));
    }

    private static void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseCatalog) {
            System.out.println(course.courseCode + ": " + course.title);
            System.out.println("Description: " + course.description);
            System.out.println("Capacity: " + course.capacity + ", Enrolled: " + course.enrolled);
            System.out.println("Available Slots: " + (course.capacity - course.enrolled));
            System.out.println();
        }
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : courseCatalog) {
            if (course.courseCode.equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
