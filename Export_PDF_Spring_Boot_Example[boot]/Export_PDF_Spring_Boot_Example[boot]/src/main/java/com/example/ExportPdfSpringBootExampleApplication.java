package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.entity.Student;
import com.example.service.StudentService;

@SpringBootApplication
public class ExportPdfSpringBootExampleApplication implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(ExportPdfSpringBootExampleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String[] names = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
        String[] emails = {"alice@example.com", "bob@example.com", "charlie@example.com", "david@example.com",
                            "emma@example.com", "frank@example.com", "grace@example.com", "henry@example.com",
                            "ivy@example.com", "jack@example.com"};
        String[] phoneNumbers = {"1111111111", "2222222222", "3333333333", "4444444444", "5555555555", 
                                "6666666666", "7777777777", "8888888888", "9999999999", "1234567890"};
        String[] addresses1 = {"Address1_A", "Address1_B", "Address1_C", "Address1_D", "Address1_E",
                                "Address1_F", "Address1_G", "Address1_H", "Address1_I", "Address1_J"};
        String[] addresses2 = {"Address2_A", "Address2_B", "Address2_C", "Address2_D", "Address2_E",
                                "Address2_F", "Address2_G", "Address2_H", "Address2_I", "Address2_J"};
        String[] policyNumbers = {"Policy1", "Policy2", "Policy3", "Policy4", "Policy5", 
                                "Policy6", "Policy7", "Policy8", "Policy9", "Policy10"};

        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setStudentName(names[i]);
            student.setEmail(emails[i]);
            student.setMobileNo(phoneNumbers[i]);
            student.setAddress1(addresses1[i]); // Set address1
            student.setAddress2(addresses2[i]); // Set address2
            student.setPolicyNumber(policyNumbers[i]); // Set policyNumber
            studentService.addStudent(student);
        }
    }
}
