package tranning;

import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Student {
    private int id;
    private String name;
    private float score1;
    private float score2;
    private float score3;
    private String status;

    public static String collage = "Can Tho University";

    private final String REGEX_NUMBER = "\\d+";
    private final String REGEX_SCORE = "(^[1-9]$)|^([1-9].[0-9]$)|10$";
    private final String PASS = "PASS";
    private final String FAILED = "FAILED";

    public Student() {}

    public Student(int id, String name, float score1, float score2, float score3) {
        this.id = id;
        this.name = name;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.status = "";
    }

    public int getId() {
        return id;
    }

    public void getInfo() {
        updateStatus();
        System.out.printf("%-10d%-10s%-10.1f%-10.1f%-10.1f%-10.1f%-10s\n", id, name, score1, score2, score3, calculateGPA(),
                status);
    }

    /**
     * Get user input information of a student
     * @return Object
     */
    public Student inputStudentInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter information of a student");

        // input student id
        System.out.print("Student ID: ");
        String id;
        do {
            id = scanner.nextLine();
            if (!isNumber(id)) {
                System.out.println("Student ID must be number");
                System.out.print("Student ID: ");
            } else if (isExist(Integer.parseInt(id))) {
                System.out.println("ID " + id + " already exist!");
                System.out.print("Student ID: ");
            }
        } while (!isNumber(id) || isExist(Integer.parseInt(id)));

        // input name
        System.out.print("Name: ");
        String name = scanner.nextLine();

        // input score 1
        System.out.print("Score 1: ");
        String score1;
        do {
            score1 = scanner.nextLine();
            if (!isScoreFormat(score1)) {
                System.out.println("Score 1 must be from 1 -> 10 ");
                System.out.print("Score 1: ");
            }
        } while (!isScoreFormat(score1));

        // input score 2
        System.out.print("Score 2: ");
        String score2;
        do {
            score2 = scanner.nextLine();
            if (!isScoreFormat(score2)) {
                System.out.println("Score 2 must be from 1 -> 10");
                System.out.print("Score 2: ");
            }
        } while (!isScoreFormat(score2));

        // input score 3
        System.out.print("Score 3: ");
        String score3;
        do {
            score3 = scanner.nextLine();
            if (!isScoreFormat(score3)) {
                System.out.println("Score 3 must be from 1 -> 10");
                System.out.print("Score 3: ");
            }
        } while (!isScoreFormat(score3));

        return new Student(Integer.parseInt(id), name, Float.parseFloat(score1), Float.parseFloat(score2),
                Float.parseFloat(score3));
    }

    //check string is number
    private boolean isNumber(String str) {
        return str.matches(REGEX_NUMBER);
    }

    // check string is score format
    private boolean isScoreFormat(String str) {
        return str.matches(REGEX_SCORE);
    }

    // calculate GPA
    public float calculateGPA() {
        return (score1 + score2 + score3)/3;
    }

    // update status
    public void updateStatus() {
        if (calculateGPA() >= 5) {
            this.status = PASS;
            return;
        }
        this.status = FAILED;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s\n", id, name, score1, score2, score3);
    }

    // get student list from file
    public List<Student> getStudentList() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./StudentInfo.txt"))) {
            list = bufferedReader.lines().map(line -> {
                String[] arr = line.split(",");
                return new Student(Integer.parseInt(arr[0]), arr[1], Float.parseFloat(arr[2]),
                        Float.parseFloat(arr[3]), Float.parseFloat(arr[4]));
            }).collect(Collectors.toList());

        }
        catch (FileNotFoundException e) {
            return Collections.emptyList();
        }
        catch (IOException ex) {
            return Collections.emptyList();
        }

        return list;
    }

    // check student id exist
    private boolean isExist(int id) {
        List<Student> studentList = getStudentList();
        if (studentList.isEmpty()) {
            return false;
        }

        for (Student student : studentList) {
            if (student.getId() == id) {
                return true;
            }
        }

        return false;
    }

    // student List with sort decrease GPA
    public List<Student> sortStudentListByDesceaseGPA() {
        List<Student> studentListSorted =
                getStudentList().stream().sorted(Comparator.comparing(Student::calculateGPA).reversed()).collect(Collectors.toList());

        return studentListSorted;
    }
    
}
