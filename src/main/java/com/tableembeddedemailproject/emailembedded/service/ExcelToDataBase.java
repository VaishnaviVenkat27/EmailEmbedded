package com.tableembeddedemailproject.emailembedded.service;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import com.tableembeddedemailproject.emailembedded.repository.MerchantRepository;
import com.tableembeddedemailproject.emailembedded.response.ApiResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Date;
import java.util.Iterator;

import static com.tableembeddedemailproject.emailembedded.constants.EmailConstants.*;

@Component
public class ExcelToDataBase {

    private static Connection con = null;

    @Autowired
    private MerchantRepository merchantRepository;


    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(ExcelToDataBase.class);

    public void exceltodb(MultipartFile file) throws IOException, SQLException, MessagingException, ClassNotFoundException {

        log.info("method starts-------------------");

        //File xlsxfile = new File(EXCEL_FILE_PATH);
        String uploadDir = EXCEL_FILE_PATH;
        File fh = new File("/tmp/");
        if (!fh.exists()) {
            fh.mkdir();
        }
// Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        final String filepath = uploadDir + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + file.getOriginalFilename());
        Files.write(path, bytes);

        FileInputStream inputStream = new FileInputStream(new File(filepath));
        log.info("file uploaded----------", inputStream);

        Workbook workbook = new XSSFWorkbook(inputStream);
        workbook.getNumberOfSheets();

        for (Sheet firstSheet : workbook) {
            for (Row row : firstSheet) {
                //if (firstSheet.getPhysicalNumberOfRows() > 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            Date helddate = row.getCell(0).getDateCellValue();
                            String mid = row.getCell(1).getStringCellValue();
                            String tid = row.getCell(2).getStringCellValue();
                            String legalName = row.getCell(3).getStringCellValue();
                            String cardnum = row.getCell(4).getStringCellValue();
                            String mcc = row.getCell(5).getStringCellValue();
                            double gross = row.getCell(6).getNumericCellValue();
                            String rrn = row.getCell(7).getStringCellValue();
                            String authid = row.getCell(8).getStringCellValue();
                            String trandate = row.getCell(9).getStringCellValue();
                            String holdremark = row.getCell(10).getStringCellValue();
                            String contact = row.getCell(11).getStringCellValue();
                            String city = row.getCell(12).getStringCellValue();
                            String region = row.getCell(13).getStringCellValue();
                            String meremail = row.getCell(14).getStringCellValue();
                            String regemail1 = row.getCell(15).getStringCellValue();
                            String regemail2 = row.getCell(16).getStringCellValue();

                            Merchant merchant = new Merchant();
                            merchant.setHelddate(helddate);
                            merchant.setMid(mid);
                            merchant.setTid(tid);
                            merchant.setLegalName(legalName);
                            merchant.setCardNumber(cardnum);
                            merchant.setMcc(mcc);
                            merchant.setGrossAmount(gross);
                            merchant.setRrn(rrn);
                            merchant.setAuthIdResp(authid);
                            merchant.setTranDate(trandate);
                            merchant.setHoldRemark(holdremark);
                            merchant.setContactNumber(contact);
                            merchant.setCity(city);
                            merchant.setRegion(region);
                            merchant.setMerchantEmail(meremail);
                            merchant.setRegionEmail(regemail1);
                            merchant.setRegionEmail2(regemail2);

                            merchantRepository.save(merchant);
                         // emailService.sendMail1();
                }
            }
        }
    }



}









