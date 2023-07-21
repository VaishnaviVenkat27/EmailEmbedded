package com.tableembeddedemailproject.emailembedded.service;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import com.tableembeddedemailproject.emailembedded.model.MerchantDetailsChargeback;
import com.tableembeddedemailproject.emailembedded.repository.MerchantDetailsChargebackRepository;
import com.tableembeddedemailproject.emailembedded.repository.MerchantRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.tableembeddedemailproject.emailembedded.constants.EmailConstants.*;

@Component
public class ExcelToDataBase {

    private static Connection con = null;

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private MerchantDetailsChargebackRepository merchantDetailsChargebackRepository;


    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(ExcelToDataBase.class);

    //    public void exceltodb(MultipartFile file) throws IOException, SQLException, MessagingException, ClassNotFoundException {
//
//        log.info("method starts-------------------");
//
//        //File xlsxfile = new File(EXCEL_FILE_PATH);
//        String uploadDir = EXCEL_FILE_PATH;
//        File fh = new File("/tmp/");
//        if (!fh.exists()) {
//            fh.mkdir();
//        }
//// Get the file and save it somewhere
//        byte[] bytes = file.getBytes();
//        final String filepath = uploadDir + file.getOriginalFilename();
//        Path path = Paths.get(uploadDir + file.getOriginalFilename());
//        Files.write(path, bytes);
//
//        FileInputStream inputStream = new FileInputStream(new File(filepath));
//        log.info("file uploaded----------", inputStream);
//
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        workbook.getNumberOfSheets();
//
//        for (Sheet firstSheet : workbook) {
//            for (Row row : firstSheet) {
//                //if (firstSheet.getPhysicalNumberOfRows() > 0) {
//                    Iterator<Cell> cellIterator = row.cellIterator();
//                    Cell cell = cellIterator.next();
//                    switch (cell.getCellType()) {
//                        case NUMERIC:
//                            Date helddate = row.getCell(0).getDateCellValue();
//                            String mid = row.getCell(1).getStringCellValue();
//                            String tid = row.getCell(2).getStringCellValue();
//                            String legalName = row.getCell(3).getStringCellValue();
//                            String cardnum = row.getCell(4).getStringCellValue();
//                            String mcc = row.getCell(5).getStringCellValue();
//                            double gross = row.getCell(6).getNumericCellValue();
//                            String rrn = row.getCell(7).getStringCellValue();
//                            String authid = row.getCell(8).getStringCellValue();
//                            String trandate = row.getCell(9).getStringCellValue();
//                            String holdremark = row.getCell(10).getStringCellValue();
//                            String contact = row.getCell(11).getStringCellValue();
//                            String city = row.getCell(12).getStringCellValue();
//                            String region = row.getCell(13).getStringCellValue();
//                            String meremail = row.getCell(14).getStringCellValue();
//                            String regemail1 = row.getCell(15).getStringCellValue();
//                            String regemail2 = row.getCell(16).getStringCellValue();
//
//                            Merchant merchant = new Merchant();
//                            merchant.setHelddate(helddate);
//                            merchant.setMid(mid);
//                            merchant.setTid(tid);
//                            merchant.setLegalName(legalName);
//                            merchant.setCardNumber(cardnum);
//                            merchant.setMcc(mcc);
//                            merchant.setGrossAmount(gross);
//                            merchant.setRrn(rrn);
//                            merchant.setAuthIdResp(authid);
//                            merchant.setTranDate(trandate);
//                            merchant.setHoldRemark(holdremark);
//                            merchant.setContactNumber(contact);
//                            merchant.setCity(city);
//                            merchant.setRegion(region);
//                            merchant.setMerchantEmail(meremail);
//                            merchant.setRegionEmail(regemail1);
//                            merchant.setRegionEmail2(regemail2);
//
//                            merchantRepository.save(merchant);
//                         // emailService.sendMail1();
//                }
//            }
//        }
//    }
    public Pair<String, Integer> exceltodb(MultipartFile file) throws IOException, SQLException, ParseException {

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
        Sheet sheet = workbook.getSheetAt(0);
        boolean firstrow = true;
        for (Row row : sheet) {
            if (firstrow) {
                firstrow = false;
                continue;
            }
            int count = 0;
            Merchant merchant = new Merchant();
            merchant.setHelddate(getCellType(row.getCell(count++)));
            merchant.setMid(getCellType(row.getCell(count++)));
            merchant.setTid(getCellType(row.getCell(count++)));
            merchant.setLegalName(getCellType(row.getCell(count++)));
            merchant.setCardNumber(getCellType(row.getCell(count++)));
            merchant.setMcc(getCellType(row.getCell(count++)));
            merchant.setGrossAmount((getCellType(row.getCell(count++))));
            merchant.setRrn(getCellType(row.getCell(count++)));
            merchant.setAuthIdResp(getCellType(row.getCell(count++)));
            merchant.setTranDate(getCellType(row.getCell(count++)));
            merchant.setHoldRemark(getCellType(row.getCell(count++)));
            merchant.setContactNumber(getCellType(row.getCell(count++)));
            merchant.setCity(getCellType(row.getCell(count++)));
            merchant.setRegion(getCellType(row.getCell(count++)));
            merchant.setMerchantEmail(getCellType(row.getCell(count++)));
            merchant.setRegionEmail(getCellType(row.getCell(count++)));
            merchant.setRegionEmail2(getCellType(row.getCell(count++)));

            Merchant merchant1 = merchantRepository.save(merchant);
            if (merchant1 == null) {
                return Pair.of("fail", 0);
            }
        }
        return Pair.of("success", 1);
    }

    public Pair<String, Integer> exceltodatabase(MultipartFile file) throws IOException, ParseException {

        String uploadDir = CHARGEBACK_EXCEL_FILE_PATH;
        File fh = new File("D:\\emailEmbedded_chargeback\\");
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

        Workbook wb = new XSSFWorkbook(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        boolean firstrow = true;
        for (Row row : sheet) {
            if (firstrow) {
                firstrow = false;
                continue;
            }
            int count = 0;
            MerchantDetailsChargeback merchantDetailsChargeback = new MerchantDetailsChargeback();

            merchantDetailsChargeback.setSubject(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setCardNumber(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setArn(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setChargebackAmount(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setTxnAmount(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setTxnDate((getCellType(row.getCell(count++))));
            merchantDetailsChargeback.setMid(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setTid(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setMerchantName(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setRrn(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setAuthCode(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setCbDate((getCellType(row.getCell(count++))));
            merchantDetailsChargeback.setReasonCode(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setLocation(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setRegion(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setContactNumber(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setMcc(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setMerchantId(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setRegionEmailId1(getCellType(row.getCell(count++)));
            merchantDetailsChargeback.setRegionEmailId2(getCellType(row.getCell(count++)));


            MerchantDetailsChargeback detailsChargeback = merchantDetailsChargebackRepository.save(merchantDetailsChargeback);
            if (detailsChargeback == null) {
                log.info("detailsChargeback -- {} ", detailsChargeback);
                return Pair.of("fail", 0);
            }
        }
        return Pair.of("success", 1);
    }

    private String getCellType(Cell cell) {
        if (cell == null) {
            return "NA";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                return String.valueOf(sdf.format(cell.getDateCellValue()));
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.BLANK) {
            return "NA";
        }
        return "NA";
    }

    public static Date parseSimpleDate(String date) throws ParseException {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yy");
            return dateFormatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String FormattedDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormatter.format(date.getTime());
    }
}









