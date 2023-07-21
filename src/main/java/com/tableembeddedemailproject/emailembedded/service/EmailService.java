package com.tableembeddedemailproject.emailembedded.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import com.tableembeddedemailproject.emailembedded.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static com.tableembeddedemailproject.emailembedded.constants.EmailConstants.*;


@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MerchantRepository merchantRepository;

    private Connection con = null;

    @Value("${db.url}")
    private String dburl;

    @Value("${db.username}")
    private String dbusername;

    @Value(("${db.password}"))
    private String dbpassword;



    public void sendMail1() throws SQLException, ClassNotFoundException, MessagingException {

        String[] cc=null;
        try {
            Class.forName(DRIVER_CLASS);
            con = DriverManager.getConnection(dburl,dbusername,dbpassword);
            Statement stmnt = con.createStatement();
            String query = "SELECT DISTINCT mid from merchant_details WHERE mid NOT IN (\"NA\")";
            ResultSet resultset1 = stmnt.executeQuery(query);
            List<String> mid = new ArrayList<>();
            while (resultset1.next()) {
                mid = Collections.singletonList(resultset1.getString(1));
                for (String merchantid : mid) {
                    logger.info("mid list----------{}-", mid);
                    String query1 = "SELECT md.id,md.auth_id_response,md.card_number,md.city,md.contact_number,md.gross_amount,md.held_date,md.hold_remark,md.legal_name,md.mcc,md.merchant_email,md.mid,md.region,md.region_emailid1,md.region_emailid2,md.rrn,md.tid,md.transaction_date FROM merchant_details md where md.mid='" + merchantid + "'";
                    logger.info("sec query starts------------{}", query1);
                    Statement stmnt1 = con.createStatement();
                    ResultSet resultset = stmnt1.executeQuery(query1);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    final String date1 = formatter.format(date);
                    MimeMessage message = sender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
                    message.setFrom("docs@bijlipay.co.in");

                    String htmltable = "Dear Customer," + "<br><br>" + "Greetings from Bijlipay!!" + "<br>" + "<br>" +
                            "At the outset, do allow us to extend our appreciation, for giving us a chance to serve you." +
                            "We perform various safety checks, in order to keep you guarded against fraudulent transactions. One of the recent transaction(s) in your terminal qualified for risk assessment." +
                            "<br><br><br>" +
                            "<html><head>"
                            + "<title>" + "Merchant Details" + date + "</title>"
                            + "</head>" + "<body>"
                            + "<tr><td><table border='2'>"

                            + "<tr  class='table table-striped' >"
                            //+"<td> <b>Id</b></td>"
                            + "<td  ><b>Held_Date</b></td>"
                            + "<td  ><b>MID</b></td>"
                            + "<td  ><b>Terminal_ID</b></td>"
                            + "<td  ><b>Legal_Name</b></td>"
                            + "<td  ><b>Card_Number</b></td>"
                            + "<td  ><b>MCC</b></td>"
                            + "<td  ><b>Gross_Amount</b></td>"
                            + "<td  ><b>RRN</b></td>"
                            + "<td  ><b>Auth_ID_Resp</b></td>"
                            + "<td  ><b>Tran_Date</b></td>"
                            + "<td  ><b>Hold_Remark</b></td>"
                            + "<td  ><b>Contact_number</b></td>"
                            + "<td  ><b>City</b></td>"
                            + "<td  ><b>Region</b></td>"
                            // + "<td  ><b>Merchant email id</b></td>"
                            //+ "<td  ><b>Region Email id</b></td>"
                            // + "<td  ><b>Region Email id</b></td>"


                            + "</tr>";

                    while (resultset.next()) {

                        int id = resultset.getInt("id");
                        String helddate = resultset.getString("held_date");
                        String mid1 = resultset.getString("mid");
                        String tid = resultset.getString("tid");
                        String legalName = resultset.getString("legal_name");
                        String cardnumber = resultset.getString("card_number");
                        String mcc = resultset.getString("mcc");
                        String Grossamount = resultset.getString("gross_amount");
                        String rrn = resultset.getString("rrn");
                        String authid = resultset.getString("auth_id_response");
                        String trandate = resultset.getString("transaction_date");
                        String holdremark = resultset.getString("hold_remark");
                        String contactnumber = resultset.getString("contact_number");
                        String city = resultset.getString("city");
                        String region = resultset.getString("region");
                        String merchantemail = resultset.getString("merchant_email");
                        String regionemail1 = resultset.getString("region_emailid1");
                        String regionemail2 = resultset.getString("region_emailid2");


                        logger.info("Merchant email id inner loop ---------{}-", merchantemail);
                        message.setSubject("REQUESTING SUPPORTING DOCUMENTS FOR - " + legalName);
                        logger.info("merchant email-----------{}",merchantemail);
                        logger.info("region email1 email-----------{}",regionemail1);
                        logger.info("region email2----------{}",regionemail2);

//                        helper.setCc(new String[]{regionemail1,regionemail2});

                        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(merchantemail));
                        InternetAddress[] internetAddresses=InternetAddress.parse(regionemail1+","+regionemail2,true);
                        message.setRecipients(Message.RecipientType.CC,internetAddresses);

                        htmltable = htmltable + "<tr>"
                                // + "<td  >" + id + "</b></td>"
                                + "<td  >" + helddate + "</b></td>"
                                + "<td  >" + mid1 + "</td>"
                                + "<td  >" + tid + "</b></td>"
                                + "<td  >" + legalName + "</td>"
                                + "<td  >" + cardnumber + "</b></td>"
                                + "<td  >" + mcc + "</td>"
                                + "<td  >" + Grossamount + "</b></td>"
                                + "<td  >" + rrn + "</td>"
                                + "<td  >" + authid + "</td>"
                                + "<td  >" + trandate + "</b></td>"
                                + "<td  >" + holdremark + "</td>"
                                + "<td  >" + contactnumber + "</td>"
                                + "<td  >" + city + "</b></td>"
                                + "<td  >" + region + "</td>"
                                 /*+ "<td  >" + merchantemail+ "</td>"
                                 + "<td  >" + regionemail1+ "</b></td>"
                                + "<td  >" +regionemail2 + "</td>"*/


                                + "</tr>";
                    }

                    htmltable = htmltable + "</table>"


                            + "</body></html>" +
                            "<br><br>" +


                            "We would like to inform you that on the scale of our risk parameters, the transaction(s) from your terminal was picked for risk assessment. So as to process your payout for the said transactions, we require some basic documents stipulated below from your end before the stipulated date."
                            + " <br><br> " + "Required Documents:" + "<br><br>" +
                            "1.      Charge slip for the transaction." + "<br>" +

                            "2.      Valid Invoice (An invoice with company name, date and amount mentioned for the provided transaction along with card holder signature)."
                            + "<br>" +
                            "3.      For transactions above Rs 50,000, a copy of the PAN card holder is mandatory" +
                            "<br>" +
                            "4.      For international transactions, both Passport & VISA copy of the card holder is mandatory."
                            + "<br>" +
                            "5.      If in case of any abnormalities identified in the submitted above documents, we would further request for cardholder declaration letter along with additional queries for clarification."
                            + "<br><br>" + " We Request you to kindly mail the above mentioned documents to 'docs@bijlipay.co.in' to complete this Risk assessment. "
                            + "<br>" + "Our investigation team will reach out to you for further verification if required. Guarding you against such Risks, is our primary concern and with your best interest in mind, be rest assured, that we will pro-actively try and safe-guard your interests." + "<br>"
                            +
                            "For any further clarification, do call us @ 1800 4200 235 or email us, 'service@bijlipay.co.in'" +
                            "<br><br>" + "Regards," + "<br>" + "Team Bijlipay";

                    message.setContent(htmltable, "text/html");
                    System.gc();
                    sender.send(message);
                }
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    public void sendingMailToMerchantBasedOnChargeback() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS);
            con = DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement stmt = con.createStatement();
            String query = "SELECT DISTINCT mid from merchant_details_chargeback where mid NOT IN (\"NA\")";
            ResultSet resultSet = stmt.executeQuery(query);
            List<String> mid=new ArrayList<>();
            while (resultSet.next()) {
                mid = Collections.singletonList(resultSet.getString("mid"));
                mid.forEach(l -> {
                    try {
                        String query1 = "select * from merchant_details_chargeback mc where mc.mid ='" + l + "'";
                    Statement statement = null;
                    MimeMessage message = sender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
                    String dateFormat = "yyyy-MM-dd";
                    final String date = currentDate(dateFormat);

                    statement = con.createStatement();
                    ResultSet resultSet1 = statement.executeQuery(query1);

                        String html = "Dear Customer," + "<br>" + "<br>" +
                                "Greetings from Bijlipay!!!"
                                + "<br>"
                                + "<br>"
                                + "We have received a dispute on the below mentioned transaction/s from the cardholder under the below reason. hence we have posted debit and the same will be recovered from settlement."
                                + "<br><br><br>"
                                + "<html><head>"
                                + "<title>" + "Merchant Details" + date + "</title>" +
                                " <style>  " +
                                "td {" +
                                " width: 100% " +
                                "} " +
                                "</style>"
                                + "</head>" + "<body>"
                                + " <tr><td><table border='2'>"
                                + "<tr  class='table table-striped' >"
                                + "<td ><b>Card_Number</b></td>"
                                + "<td   ><b>ARN</b></td>"
                                + "<td  ><b>ChargeBack_Amount</b></td>"
                                + "<td ><b>Txn_Amount</b></td>"
                                + "<td  ><b>Txn_Date</b></td>"
                                + "<td   ><b>Mid</b></td>"
                                + "<td  ><b>Tid</b></td>"
                                + "<td  ><b>Merchant_Name</b></td>"
                                + "<td ><b>RRN</b></td>"
                                + "<td   ><b>AuthCode</b></td>"
                                + "<td  ><b>CB_Date</b></td>"
                                + "<td ><b>Reason_Code</b></td>"
                                + "<td ><b>location</b></td>"
                                + "<td ><b>region</b></td>"
                                + "<td ><b>contact_Number</b></td>"
                                + "</tr>";
                        while (resultSet1.next()) {

                            message.setFrom("docs@bijlipay.co.in");
                            String cardNumber = resultSet1.getString("card_number");
                            String arn = resultSet1.getString("arn");
                            double chargebackAmount = resultSet1.getDouble("chargeback_amount");
                            double txnAmount = resultSet1.getDouble("txn_amount");
                            String txnDate = resultSet1.getString("txn_date");
                            String Mid = resultSet1.getString("mid");
                            String tid = resultSet1.getString("tid");
                            String merchantName = resultSet1.getString("merchant_name");
                            String rrn = resultSet1.getString("rrn");
                            String authCode = resultSet1.getString("auth_code");
                            String cbDate = resultSet1.getString("cb_date");
                            String reasonCode = resultSet1.getString("reason_code");
                            String location = resultSet1.getString("location");
                            String region = resultSet1.getString("region");
                            String contactNumber = resultSet1.getString("contact_number");
                            String merchantId = resultSet1.getString("merchant_id");
                            String regionEmailId1 = resultSet1.getString("region_email_id1");
                            String regionEmailId2 = resultSet1.getString("region_email_id2");

                            String subject = resultSet1.getString("subject");

                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(merchantId));
                            InternetAddress[] internetAddresses = InternetAddress.parse(regionEmailId1 + "," + regionEmailId2, true);
                            message.setRecipients(Message.RecipientType.CC, internetAddresses);

                            message.setSubject(subject + " DOCUMENTATION REQUEST - " + merchantName);
                            html = html + "<tr>"
                                    + "<td  >" + cardNumber + "</b></td>"
                                    + "<td  >" + arn + "</td>"
                                    + "<td  >" + chargebackAmount + "</b></td>"
                                    + "<td  >" + txnAmount + "</td>"
                                    + "<td  >" + txnDate + "</b></td>"
                                    + "<td  >" + Mid + "</td>"
                                    + "<td  >" + tid + "</b></td>"
                                    + "<td  >" + merchantName + "</td>"
                                    + "<td  >" + rrn + "</td>"
                                    + "<td  >" + authCode + "</b></td>"
                                    + "<td  >" + cbDate + "</td>"
                                    + "<td  >" + reasonCode + "</td>"
                                    + "<td  >" + location + "</td>"
                                    + "<td  >" + region + "</td>"
                                    + "<td  >" + contactNumber + "</td>"
                                    + "</tr>" ;
                        }
                        html = html + "</table>" + "<br>" + "<br>"
                                + " In order to represent and obtain the credit for the same, kindly share below mentioned documents for the disputed transaction within <b style=\"color:#FF0000\">" + afterFiveDays(dateFormat) + "</b>."
                                + "<br>"
                                + "</body></html>"
                                + " <br><br>" + "<b>Required Documents:</b>" + "<br><br>"
                                + "1. Valid Invoice (An invoice with company name, date and amount as per the transaction summary and sign of cardholder, for the given transaction) and also an acknowledgement that goods have been delivered or service was rendered to cardholder."
                                + "<br>"
                                + "2. For International transactions, a copy of Passport and VISA issued to cardholder."
                                + "<br>"
                                + "3. Any other documentation pertaining to this transaction."
                                + "<br>"
                                + "4. An Explanation letter from your end rebutting dispute from the cardholder."
                                + "<br>"
                                + "We Request you to kindly mail the above mentioned documents to docs@bijlipay.co.in to represent the case."
                                + "<br>"
                                + "<br>"
                                + "Our investigation team will reach out to you for further investigation if required."
                                + "<br>"
                                + "<br>"
                                + "For any further clarification, do call us @ 1800 4200 235 or email us, service@bijlipay.co.in"
                                + "<br>"
                                + "<br>"
                                + "<br>"
                                + "<h3>Regards</h3>"
                                + "<h3>Bijlipay team</h3>";

                        // }
                        message.setContent(html, "text/html");
                        System.gc();
                        sender.send(message);

                    } catch (SQLException | MessagingException | ParseException | MailException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public List<Merchant> getMerchant() {
        return merchantRepository.getMerchant();

    }

    public void truncate() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS);
            con = DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement stmnt = con.createStatement();
            String query = "TRUNCATE table merchant_details";
            int rowsaffected= stmnt.executeUpdate(query);
            logger.info("data deleted successfully!!!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    public static String currentDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        String currentDate = formatter.format(date);
        return currentDate;
    }
    public static String afterFiveDays(String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,5);

        Date date1=calendar.getTime();
        String after5Days=formatter.format(date1);
        return after5Days;
    }


    public void truncateChargebackDetails() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS);
            con = DriverManager.getConnection(dburl, dbusername, dbpassword);
            Statement stmnt = con.createStatement();
            String query = "TRUNCATE table merchant_details_chargeback";
            int rowsaffected= stmnt.executeUpdate(query);
            logger.info("data deleted successfully!!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
