package com.hms;

import java.util.Date;

public class InvoiceModel {
    private int invoiceID;
    private double amount;
    private Date date;
    private String type;
    private String reference;
    private String status;

    public InvoiceModel(int invoiceID, double amount, Date date, String type, String reference, String status) {
        this.invoiceID = invoiceID;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.reference = reference;
        this.status = status;
    }

    public int getInvoiceID() { return invoiceID; }
    public void setInvoiceID(int invoiceID) { this.invoiceID = invoiceID; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}