package com.tableembeddedemailproject.emailembedded.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="merchant_details")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="held_date")
    private Date helddate;

    @Column(name="mid")
    private String mid;

    @Column(name="tid")
    private String tid;

    @Column(name="legal_name")
    private String legalName;

    @Column(name="card_number")
    private String cardNumber;
    @Column(name="mcc")
    private String mcc;

    @Column(name="gross_amount")
    private double grossAmount;

    @Column(name="rrn")
    private String rrn;
    @Column(name="auth_id_response")
    private String authIdResp;

    @Column(name="transaction_date")
    private String tranDate;

    @Column(name="hold_remark")
    private String holdRemark;
    @Column(name="contact_number")
    private String contactNumber;
    @Column(name="city")
    private String city;

    @Column(name="region")
    private String region;

    @Column(name="merchant_email")
    private String merchantEmail;

    @Column(name="region_emailid1")
    private String regionEmail;

    @Column(name="region_emailid2")
    private String regionEmail2;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHelddate() {
        return helddate;
    }

    public void setHelddate(Date helddate) {
        this.helddate = helddate;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getAuthIdResp() {
        return authIdResp;
    }

    public void setAuthIdResp(String authIdResp) {
        this.authIdResp = authIdResp;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getHoldRemark() {
        return holdRemark;
    }

    public void setHoldRemark(String holdRemark) {
        this.holdRemark = holdRemark;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public String getRegionEmail() {
        return regionEmail;
    }

    public void setRegionEmail(String regionEmail) {
        this.regionEmail = regionEmail;
    }

    public String getRegionEmail2() {
        return regionEmail2;
    }

    public void setRegionEmail2(String regionEmail2) {
        this.regionEmail2 = regionEmail2;
    }
}
