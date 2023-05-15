package com.tableembeddedemailproject.emailembedded.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tableembeddedemailproject.emailembedded.model.Merchant;
import com.tableembeddedemailproject.emailembedded.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
            String query = "SELECT DISTINCT mid from merchant_details";
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
                    helper.setFrom("docs@bijlipay.co.in");

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
                            + "<td  ><b>HeldDate</b></td>"
                            + "<td  ><b>MID</b></td>"
                            + "<td  ><b>Terminal ID</b></td>"
                            + "<td  ><b>Legal Name</b></td>"
                            + "<td  ><b>Card Number</b></td>"
                            + "<td  ><b>MCC</b></td>"
                            + "<td  ><b>Gross Amount</b></td>"
                            + "<td  ><b>RRN</b></td>"
                            + "<td  ><b>Auth ID Resp</b></td>"
                            + "<td  ><b>Tran Date</b></td>"
                            + "<td  ><b>Hold Remark</b></td>"
                            + "<td  ><b>Contact number</b></td>"
                            + "<td  ><b>City</b></td>"
                            + "<td  ><b>Region</b></td>"
                            // + "<td  ><b>Merchant email id</b></td>"
                            //+ "<td  ><b>Region Email id</b></td>"
                            // + "<td  ><b>Region Email id</b></td>"


                            + "</tr>";

                    while (resultset.next()) {
                        logger.info("while loop starts------------{}");
                        int id = resultset.getInt(1);
                        String helddate = resultset.getString(7);
                        String mid1 = resultset.getString(12);
                        String tid = resultset.getString(17);
                        String legalName = resultset.getString(9);
                        String cardnumber = resultset.getString(3);
                        String mcc = resultset.getString(10);
                        String Grossamount = resultset.getString(6);
                        String rrn = resultset.getString(16);
                        String authid = resultset.getString(2);
                        String trandate = resultset.getString(18);
                        String holdremark = resultset.getString(8);
                        String contactnumber = resultset.getString(5);
                        String city = resultset.getString(4);
                        String region = resultset.getString(13);
                        String merchantemail = resultset.getString(11);
                        String regionemail1 = resultset.getString(14);
                        String regionemail2 = resultset.getString(15);


                        logger.info("Merchant email id inner loop ---------{}-", merchantemail);
                        helper.setSubject("REQUESTING SUPPORTING DOCUMENTS FOR - " + legalName);
                        logger.info("merchant email-----------{}",merchantemail);
                        logger.info("region email1 email-----------{}",regionemail1);
                        logger.info("region email2----------{}",regionemail2);
                        //helper.setTo(SENT_TO);
                        helper.setTo(merchantemail);
                        helper.setCc(new String[]{regionemail1,regionemail2,"docs@bijlipay.co.in","service@bijlipay.co.in"});
                       // helper.setCc(regionemail2);


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
}
