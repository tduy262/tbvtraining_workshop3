package tranning;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(Student.collage);

        // 2.
        Student student1 = new Student(1, "Duy", 9, 9, 9);
        System.out.println("Information of student: ");
        student1.getInfo();
        System.out.println();
        System.out.println();

        // 3.
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        do {
            // Best Student
            System.out.println("************************** BEST STUDENT ****************************");
            List<Student> students = student.sortStudentListByDesceaseGPA();
            if (students.isEmpty()) {
                System.out.println("No data in file");
            } else {

                System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n", "ID", "Name", "Score 1", "Score 2",
                        "Score 3", "GPA" , "Status");
                students.get(0).getInfo();

            }
            System.out.println("********************************************************************");

            System.out.println("You have options: ");
            System.out.println("1.Input student info");
            System.out.println("2.Show student List");
            System.out.println("3.Show student list sorted by GPA");
            System.out.println("0.Exit");
            System.out.print("Your choice: ");
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    System.out.println("--------------------------------END---------------------------------");
                    stop = true;
                    break;
                case "1":
                    try {
                        student = student.inputStudentInfo();

                        FileWriter path = new FileWriter("./StudentInfo.txt", true);
                        BufferedWriter writer = new BufferedWriter(path);
                        writer.write(student.toString());

                        System.out.println("Save information of student successfully!");
                        writer.close();
                    } catch (IOException i) {
                        System.out.println("write data failed!");
                        i.printStackTrace();
                    }
                    break;
                case "2":
                    System.out.println("List student of " + Student.collage + ":");
                    if (student.getStudentList().isEmpty()) {
                        System.out.println("List Student is empty!");
                    } else {
                        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n", "ID", "Name", "Score 1", "Score 2",
                                "Score 3", "GPA" , "Status");
                        student.getStudentList().forEach(Student::getInfo);
                    }
                    break;
                case "3":
                    System.out.println("Student Rankings of " + Student.collage +":");

                    if (student.getStudentList().isEmpty()) {
                        System.out.println("List Student is empty!");
                    } else {
                        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n", "ID", "Name", "Score 1", "Score 2",
                                "Score 3", "GPA" , "Status");
                        student.sortStudentListByDesceaseGPA().forEach(Student::getInfo);
                    }
                    break;
                default:
                    System.out.println("Option invalid");
                    break;
            }
            System.out.println("--------------------------------------------------------------------");
        } while (!stop);
    }
}
