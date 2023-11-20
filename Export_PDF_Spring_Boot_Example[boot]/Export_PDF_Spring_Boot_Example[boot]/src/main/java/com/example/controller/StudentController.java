package com.example.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.entity.Student;
import com.example.service.StudentService;
import com.example.util.PdfGenerator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @GetMapping("/export-to-pdf")
    public void generatePdfFile(HttpServletResponse response) throws IOException {
        String filePath = "D:\\pdf\\id_text.html";
        File input = new File(filePath);

        if (input.exists()) {
            // Read HTML content from the file
            String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // Get the content from the div with id "idno" as userInput
            String userInput = extractContentFromDiv(htmlContent, "idno");

            if (userInput != null && !userInput.isEmpty()) 
            {
                int userId = Integer.parseInt(userInput);
                Student student = studentService.getStudentById(userId);
                if (student != null) 
                {
                    pdfGenerator.generate(student);
                    return; // PDF generated, exit the method
                }
            }
        }

        // Handle the case when the student is not found or div content is invalid
        response.getWriter().println("Unable to generate PDF. Invalid user input or student not found.");
    }
    
    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/showdetails")
    public String showStudentDetails(Model model) throws IOException {
        String filePath = "D:\\pdf\\id_text.html";
        File input = new File(filePath);

        if (input.exists()) {
            // Read HTML content from the file
            String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // Get the content from the div with id "idno" as userInput
            String userInput = extractContentFromDiv(htmlContent, "idno");

            if (userInput != null && !userInput.isEmpty()) {
                try {
                    int userId = Integer.parseInt(userInput);
                    Student student = studentService.getStudentById(userId);

                    if (student != null) {
                        // Thymeleaf context for student details
                        Context context = new Context();
                        context.setVariable("id", student.getId());
                        context.setVariable("name", student.getStudentName());
                        context.setVariable("address1", student.getAddress1()); // Add address1 variable
                        context.setVariable("address2", student.getAddress2()); // Add address2 variable
                        context.setVariable("policyNumber", student.getPolicyNumber()); // Add policyNumber variable

                        // Add other variables needed for the template

                        // Process HTML template with Thymeleaf
                        String processedHtml = templateEngine.process("student-details", context);
                        model.addAttribute("studentDetails", processedHtml);
                        return "display-html"; // Assuming "student-detail.html" is your Thymeleaf template for student details
                    } else {
                        model.addAttribute("studentDetails", "Student not found for ID: " + userId);
                        return "error-student-not-found"; // Assuming "error-student-not-found.html" for error message
                    }
                } catch (NumberFormatException ex) {
                    model.addAttribute("studentDetails", "Invalid user input: " + userInput);
                    return "error-invalid-input"; // Assuming "error-invalid-input.html" for invalid input error
                }
            }
        }

        model.addAttribute("studentDetails", "Unable to retrieve student details");
        return "error-retrieve-details"; // Assuming "error-retrieve-details.html" for error in retrieving details
    }
  
        


    // Method to extract content from a div element within HTML content
    private String extractContentFromDiv(String htmlContent, String divId) {
        Document doc = Jsoup.parse(htmlContent);
        Element divElement = doc.getElementById(divId);

        if (divElement != null) {
            return divElement.text().trim();
        } else {
            return "5"; // Handle if the div with the specified ID is not found
        }
    }
    
}
