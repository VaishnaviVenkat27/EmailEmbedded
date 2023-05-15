package com.tableembeddedemailproject.emailembedded.controller;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import com.tableembeddedemailproject.emailembedded.response.ApiResponse;
import com.tableembeddedemailproject.emailembedded.service.EmailService;
import com.tableembeddedemailproject.emailembedded.service.ExcelToDataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static com.tableembeddedemailproject.emailembedded.constants.EmailConstants.*;

@RestController
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelToDataBase excelToDataBase;

    private Connection con = null;


    @CrossOrigin("*")
    @PostMapping("/riskmanage")
    public ResponseEntity<?> sendEmail(@RequestParam("file") MultipartFile file) throws SQLException, IOException, MessagingException, ClassNotFoundException {
        String fileExtension = "";
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "File is empty", null), HttpStatus.BAD_REQUEST);
        }
        int index = file.getOriginalFilename().lastIndexOf(".");
        if (index > 0) {
            fileExtension = file.getOriginalFilename().substring(index + 1); // return ---> "java"
        }
        if (!fileExtension.equals("xlsx")) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "invalid file format"), HttpStatus.BAD_REQUEST);
        }
            System.gc();
            emailService.truncate();
            excelToDataBase.exceltodb(file);
            try {
                logger.info("send method starts------------------");
                emailService.sendMail1();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully!!!");

    }

    @GetMapping("/merchant")
    public ResponseEntity<?> getMerchantDetails(@RequestParam("file") MultipartFile file) throws SQLException, IOException, MessagingException, ClassNotFoundException {

        excelToDataBase.exceltodb(file);
        List<Merchant> merchant = emailService.getMerchant();
        if (merchant != null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Merchant Details are found", merchant), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Merchant Details are not found", null), HttpStatus.NOT_FOUND);
    }


}
