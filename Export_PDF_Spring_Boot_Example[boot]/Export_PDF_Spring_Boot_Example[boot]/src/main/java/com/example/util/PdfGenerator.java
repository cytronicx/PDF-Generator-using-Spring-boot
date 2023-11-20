package com.example.util;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.example.entity.Student;
import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

	// Import statements here

@Component
public class PdfGenerator {

    @Autowired
    private TemplateEngine templateEngine;

    public void generate(Student student) throws IOException, DocumentException {
        Context context = new Context();
        
        context.setVariable("id", student.getId());
        context.setVariable("name", student.getStudentName());
        context.setVariable("address1", student.getAddress1()); // Add address1 variable
        context.setVariable("address2", student.getAddress2()); // Add address2 variable
        context.setVariable("policyNumber", student.getPolicyNumber()); // Add policyNumber variable

        // Add other variables needed for the template

        String processedHtml = templateEngine.process("student-details", context);

        // File path to save the PDF
        String pdfFilePath = "D:\\pdf\\StudentDetails.pdf";

        // Generate PDF using Flying Saucer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processedHtml);
        renderer.layout();

        try (FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
            renderer.createPDF(fos);
        }
    }
}