package com.tableembeddedemailproject.emailembedded.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant_details_chargeback")
public class MerchantDetailsChargeback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "arn")
    private String arn;
    @Column(name = "chargeback_amount")
    private String chargebackAmount;
    @Column(name = "txn_amount")
    private String txnAmount;
    @Column(name = "txn_date")
    private String txnDate;
    @Column(name = "mid")
    private String mid;
    @Column(name = "tid")
    private String tid;
    @Column(name = "merchant_name")
    private String merchantName;
    @Column(name = "rrn")
    private String rrn;
    @Column(name = "auth_code")
    private String authCode;
    @Column(name = "cb_date")
    private String cbDate;
    @Column(name = "reason_code")
    private String reasonCode;
    @Column(name = "location")
    private String location;
    @Column(name = "region")
    private String region;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "mcc")
    private String mcc;
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "region_email_id1")
    private String regionEmailId1;
    @Column(name = "region_email_id2")
    private String regionEmailId2;
    @Column(name = "subject")
    private String subject;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getChargebackAmount() {
        return chargebackAmount;
    }

    public void setChargebackAmount(String chargebackAmount) {
        this.chargebackAmount = chargebackAmount;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCbDate() {
        return cbDate;
    }

    public void setCbDate(String cbDate) {
        this.cbDate = cbDate;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getRegionEmailId1() {
        return regionEmailId1;
    }

    public void setRegionEmailId1(String regionEmailId1) {
        this.regionEmailId1 = regionEmailId1;
    }

    public String getRegionEmailId2() {
        return regionEmailId2;
    }

    public void setRegionEmailId2(String regionEmailId2) {
        this.regionEmailId2 = regionEmailId2;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "MailMergeContent{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", arn='" + arn + '\'' +
                ", chargebackAmount='" + chargebackAmount + '\'' +
                ", txnAmount='" + txnAmount + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", rrn='" + rrn + '\'' +
                ", authCode='" + authCode + '\'' +
                ", cbDate='" + cbDate + '\'' +
                ", reasonCode='" + reasonCode + '\'' +
                ", location='" + location + '\'' +
                ", region='" + region + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", mcc='" + mcc + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", regionEmailId1='" + regionEmailId1 + '\'' +
                ", regionEmailId2='" + regionEmailId2 + '\'' +
                ", subject='" + subject + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
