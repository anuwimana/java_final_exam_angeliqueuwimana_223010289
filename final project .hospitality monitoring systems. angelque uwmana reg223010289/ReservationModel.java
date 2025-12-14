package com.hms;


import java.util.Date;

public class ReservationModel {
    private int reservationID;
    private String orderNumber;
    private Date date;
    private String status;
    private double totalAmount;
    private String paymentMethod;
    private String notes;
    private int guestID;
    private int roomID;

    public ReservationModel(int reservationID, String orderNumber, Date date, String status,
                            double totalAmount, String paymentMethod, String notes,
                            int guestID, int roomID) {
        this.reservationID = reservationID;
        this.orderNumber = orderNumber;
        this.date = date;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        this.guestID = guestID;
        this.roomID = roomID;
    }

    // Getters and setters
    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }
    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
}
